#!/usr/bin/php
<?php
//	old way to generate
//	printf("uniqid: %s \n", md5(uniqid("cat",true)));

//generate 30 char string
$permitted_chars = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
$uid = substr(str_shuffle($permitted_chars), 0, 30);
echo $uid;

?>
