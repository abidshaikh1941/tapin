<?php

    class GetDriver{
        private $con;
        public $details=array();
        function __construct(){
            
            require_once dirname(__FILE__).'/DbConnect.php';
            $db = new DbConnection();
            $this->con = $db->connect();
        }

        public    function getDriver($Phone){
             

               $stmt = $this->con->prepare("SELECT Firstname,Lastname,Address,Gender,Dob,License,Verified FROM `DRIVER_NEW`  WHERE Phone= ? ;");
                $stmt->bind_param("s",$Phone);
               
                if($stmt->execute())
                {
                    	                $temp=array();

                   $stmt->bind_result($Firstname, $Lastname,$Address,$Gender,$Dob,$License,$Verified);
                   $stmt->fetch();
                   $details['Firstname']=$Firstname;
                   $details['Lastname']=$Lastname;
                   $details['Address']=$Address;
                   $details['Gender']=$Gender;
                   $details['Dob']=$Dob;
                   $details['License']=$License;
                   $details['Verified']=$Verified;
                   $details['msg']="success";
                   /*while($stmt->fetch()){
                    	//pushing fetched data in an array 
                    	$temp = [
                    		'Firstname'=>$Firstname,
                    		'Lastname'=>$Lastname
                    	];
                    	
                    	//pushing the array inside the hero array 
                    	array_push($details, $temp);
                                }*/
                        return $details;
                }
                else
                {
                    $details['msg']="2";
                    return $details;
                    
                }
            
        }
        
        
    }

?>