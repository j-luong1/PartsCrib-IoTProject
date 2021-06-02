drop table Inventory;
create table Inventory
 (
    SID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    Category varchar(20),
    Name varchar(20),
    Description varchar (20),
    Quantity numeric(3,0),
    Image varchar (30)
 );

 Insert into Inventory (Category,Name,Description,Quantity,Image) values("Tools","ScrewDriver","",50,"screwdriver.jpg"),
                             ("Tools","Hammer","This is a Hammer",5,"hammer.jpg"),
                             ("Tools","Multimeter","Measure voltage, resistance, amperage",10,""),
                             ("Resistor","1.5k","",50,""),
                             ("Resistor","1k","",100,""),
                             ("Resistor","100","",50,"");


  INSERT into Inventory (Category,Name,Description,Quantity) values("Analog_ICs","CD4051","",17),("Analog_ICs","H11A1","",17),("Analog_ICs","INA125","",25),("Analog_ICs","LM301","",26),("Analog_ICs","LM3086","",51),("Analog_ICs","LM317","",21),("Analog_ICs","LM324","",42),("Analog_ICs","LM3524","",13),("Analog_ICs","LM555","",44),("Analog_ICs","LM565","",19),("Analog_ICs","LM741","",15),("Analog_ICs","LM7805","",33),("Analog_ICs","LM7812","",16),("Analog_ICs","LM7905","",47),("Analog_ICs","LM7912","",10),("Analog_ICs","MOC3011","",39),("Analog_ICs","MOC3020","",50),("Analog_ICs","MOC3031","",39);
 
 INSERT into Inventory (Category,Name,Description,Quantity) values("Boards","Arduino_UNO_R3+USB","",42),("Boards","Codec","",49),("Boards","DAQ","",12),("Boards","Discrete_PLL","",53),("Boards","DTMF_Decoder","",13),("Boards","Feedback_Controller","",37),("Boards","FPGA Basys 3 Artix-7+USB","",19),("Boards","H_Bridqe Driver","",54),("Boards","HCS12+PowerSupply","",42),("Boards","LED/Relay_Board","",27),("Boards","LED/Switch_Board","",17),("Boards","LogicBoard","",11),("Boards","PCM_System","",35),("Boards","Raspberry_Pi_KlT","",24),("Boards","RS-232","",27),("Boards","SolarTracker+ PowerSupply","",52),("Boards","Thermistor_Circuit","",20),("Boards","XR2206_Freq_Gen","",38);
 
 INSERT into Inventory (Category,Name,Description,Quantity) values("Cables","1-1_Scope_Probes","",24),("Cables","10-1_Scope_Probes","",26),("Cables","25PinF-FAdapter","",48),("Cables","9-25PinAdapter","",29),("Cables","A_B-USB_Arduino","",50),("Cables","HDMI-HDMI","",55),("Cables","HDMI-VGA","",21),("Cables","Pair_Red/Black_Multimeter","",50),("Cables","RJ45_Crossover","",38),("Cables","RJ4S_Straight-Through","",16),("Cables","Serial-Ethernet(Console)","",34),("Cables","Serial-Serial","",45),("Cables","Serial-USB","",49),("Cables","USB Micro (FPGA)","",27),("Cables","USE-Ethernet","",47),("Cables","VGA-VGA","",46);
 
 INSERT into Inventory (Category,Name,Description,Quantity) values("Digital_ICs","1Mhz_Osc","",29),("Digital_ICs","6264","",10),("Digital_ICs","7400","",30),("Digital_ICs","7402","",16),("Digital_ICs","7403","",28),("Digital_ICs","7404","",37),("Digital_ICs","7406","",17),("Digital_ICs","7408","",53),("Digital_ICs","7410","",52),("Digital_ICs","74138","",34),("Digital_ICs","7414","",49),("Digital_ICs","74151","",16),("Digital_ICs","74157","",17),("Digital_ICs","74194","",32),("Digital_ICs","7420","",31),("Digital_ICs","74244","",33),("Digital_ICs","74245","",24),("Digital_ICs","74247","",51),("Digital_ICs","74283","",27),("Digital_ICs","7430","",23),("Digital_ICs","7432","",17),("Digital_ICs","7442","",22),("Digital_ICs","7473","",52),("Digital_ICs","7474","",23),("Digital_ICs","7475","",46),("Digital_ICs","7486","",41),("Digital_ICs","7490","",17),("Digital_ICs","7493","",18),("Digital_ICs","7495","",33),("Digital_ICs","74HC14","",53),("Digital_ICs","ADC084","",39),("Digital_ICs","CD4011","",52),("Digital_ICs","CD4047","",54),("Digital_ICs","DAC0808","",13),("Digital_ICs","GAL16V8","",12),("Digital_ICs","MC1488","",27),("Digital_ICs","MC1489","",41);
  
 INSERT into Inventory (Category,Name,Description,Quantity) values("Diodes","1N4003","",24),("Diodes","1N4005","",37),("Diodes","1N4148","",44),("Diodes","1N4732","",18),("Diodes","1N4734","",44),("Diodes","1N4735","",51),("Diodes","1N4744A","",41),("Diodes","1N914","",19),("Diodes","LED_7_Seg","",29),("Diodes","LED_Green","",55),("Diodes","LED_Red","",15),("Diodes","LED_Yellow","",47),("Diodes","Inductors","",23),("Diodes","100_mH","",22),("Diodes","2.2_mH","",13),("Diodes","27_mH","",19),("Diodes","470_uH","",36),("Diodes","47_mH","",50),("Diodes","8.2_mH","",51);
 
  INSERT into Inventory (Category,Name,Description,Quantity) values("Misc","12V_Lamp","",12),("Misc","12V_Relay","",28),("Misc","455KHz_Ceramic_FiIter","",42),("Misc","5V_Relay","",41),("Misc","Analog_MultiMeter","",24),("Misc","BreadBoard","",40),("Misc","Breakout_Box","",45),("Misc","Comp_Keyboard","",27),("Misc","Comp_Mouse","",11),("Misc","DTMF_Telephone","",30),("Misc","Ethernet_CableTester","",37),("Misc","Function_Generator","",26),("Misc","HC512_Power_SuppIy","",11),("Misc","Helping_Hand","",25),("Misc","Laptop","",35),("Misc","Missing_ltem","",46),("Misc","Modular_Plug","",21),("Misc","Neon-Lamp","",20),("Misc","Oscilloscope","",55),("Misc","PCB_Holder","",41),("Misc","PhotoCell","",20),("Misc","PhotoResistor","",15),("Misc","PLCUnit+Laptop","",32),("Misc","Reed Switch","",33),("Misc","RPI_Power_Supply","",28),("Misc","Soldering_Iron","",36),("Misc","Soldering_Unit","",43),("Misc","Thermistor_4.7K","",55),("Misc","Thermocouple","",30);
 
 INSERT into Inventory (Category,Name,Description,Quantity) values("Motors","DC_Gear_Motor","",43),("Motors","Servo_Motor","",33),("Motors","Stepper_Motor","",11);
 
 INSERT into Inventory (Category,Name,Description,Quantity) values("Potentiometer","10_KOhm_Pot","",18),("Potentiometer","1_KOhm_Pot","",26),("Potentiometer","50_KOhm_Pot","",14);
 
  INSERT into Inventory (Category,Name,Description,Quantity) values("RF_Connectors","Band_PFilter","",43),("RF_Connectors","BNC_F-BNC_F","",16),("RF_Connectors","BNC_M-BNC_M","",29),("RF_Connectors","BNC_Right_Angle-M_F","",24),("RF_Connectors","BNC_Tee-FMF","",33),("RF_Connectors","BNC_Term-50","",31),("RF_Connectors","BNC Term-Mismatch","",40),("RF_Connectors","F_F-N_M","",12),("RF_Connectors","High_P Filter","",21),("RF_Connectors","Low_P Filter","",51),("RF_Connectors","N_F-N_F","",22),("RF_Connectors","N_M-BNC_F","",21),("RF_Connectors","N_M-BNC_M","",37),("RF_Connectors","N_M-N_M","",32),("RF_Connectors","N_M-TNC_f","",26),("RF_Connectors","N_Right_Angle-M_F","",13),("RF_Connectors","N_Tee_FFF","",10),("RF_Connectors","N_Teee_FMF","",45),("RF_Connectors","N_Term-5O","",40),("RF_Connectors","RCA_M-BNC_F","",44),("RF_Connectors","SMA_F-N_M","",44),("RF_Connectors","TNC_F-TNC_F","",15),("RF_Connectors","UHF_F-BNC_M","",27),("RF_Connectors","UHF_F-TNC_M","",32),("RF_Connectors","UHF_M-BNC_F","",38),("RF_Connectors","UHF_M-N_F","",28),("RF_Connectors","UHF_Right_Angle-M_F","",51),("RF_Connectors","UHF_Tee-FMF","",47),("RF_Connectors","UHF_Term_50","",44);
 
  INSERT into Inventory (Category,Name,Description,Quantity) values("Tools","BIX_PunchDown_Tool","",45),("Tools","Ethernet_PunchDown_Tool","",31),("Tools","EthernetCrimp","",17),("Tools","Glue_Gun","",18),("Tools","Hack_Saw","",46),("Tools","Heat_Gun","",40),("Tools","Plastic-ScrewDriver","",40),("Tools","Pliers","",30),("Tools","ScrewDriver","",42),("Tools","Side_Cutters","",51),("Tools","Wire-Cutters","",26);
 
 INSERT into Inventory (Category,Name,Description,Quantity) values("Transformers","TR_12.6V","",16),("Transformers","TR_455KHz_IF","",53),("Transformers","TR_Audio","",32),("Transformers","TR_Hybrid","",32),("Transformers","TR_Vari-AC","",11);
 
 INSERT into Inventory (Category,Name,Description,Quantity) values("Transistors_MOSFETs","2N3055T","",47),("Transistors_MOSFETs","2N3905","",24),("Transistors_MOSFETs","2N3906","",18),("Transistors_MOSFETs","2N4124","",18),("Transistors_MOSFETs","2N4126","",54),("Transistors_MOSFETs","2N4870","",17),("Transistors_MOSFETs","2N6027","",18),("Transistors_MOSFETs","B5270","",20),("Transistors_MOSFETs","C106_SCR","",22),("Transistors_MOSFETs","IRF520","",14),("Transistors_MOSFETs","IRF9520","",53),("Transistors_MOSFETs","IRL520","",36),("Transistors_MOSFETs","MV2201","",50),("Transistors_MOSFETs","SC146D","",32),("Transistors_MOSFETs","Var.","",54);
