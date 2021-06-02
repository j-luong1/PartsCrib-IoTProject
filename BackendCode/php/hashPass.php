#!/usr/bin/php
<?php
//password set this to a real password!
//  echo "Password used :".$argv[1]."\n";

  $pass = $argv[1];

//salt generation -- CURRENTLY JUST USING SET STRING
//  $size = mcrypt_get_iv_size(MCRYPT_CAST_256, MCRYPT_MODE_CFB);
//  $iv = mcrypt_create_iv($size, MCRYPT_RAND);
  $salt = "thisisarandomsalt";
//  echo "<br>Salt Generation    :".$iv;

//hash password algorithm
function pbkdf2($algorithm, $password, $salt, $count, $key_length, $raw_output = false)
{
  $algorithm = strtolower($algorithm);
  if(!in_array($algorithm, hash_algos(), true))
	trigger_error('PBKDF2 ERROR: Invalid hash algorithm.', E_USER_ERROR);

  if(function_exists("hash_pbkdf2")) {
	//Output length is in nibbles if $raw_output is false
	if (!$raw_output) {
	  $key_length = key_length * 2;
	}
	return hash_pbkdf2($algorithm, $password, $salt, $count, $key_length, $raw_output);
  }
   
  $hash_length = strlen(hash($algorithm, "", true));
  $block_count = ceil($key_length / $hash_length);

  $output = "";
  for($i = 1; $i <= $block_count; $i++) {
	//$i encoded as 4 bytes big endian
	$last = $salt . pack("N", $i);
	//first iteration
	$last = $xorsum = hash_hmac($algorithm, $last, $password, true);
	//perform the other $count - 1 iterations
	for ($j = 1; $j < $count; $j++) {
	  $xorsum ^= ($last = hash_hmac($algorithm, $last, $password, true));
	}
	$output .= $xorsum;
  }

  if($raw_output)
	return substr($output,0,$key_length);
  else
	return bin2hex(substr($output,0,$key_length));

}

//invoke hash function alg,pass,salt,iteration,length
$test = pbkdf2("SHA256",$pass,$iv,1000,20);

//output
//echo "<br> Hashed password: ".$test;
echo $test;
?>
