<?php

    class GetUser{
        private $con;
        private $details;
        function __construct(){
            
            require_once dirname(__FILE__).'/DbConnect.php';
            $db = new DbConnection();
            $this->con = $db->connect();
        }

        public    function getUser($Phone){
             
                        $this->details = array();
               $stmt = $this->con->prepare("SELECT Firstname,Lastname FROM `USER_NEW`  WHERE Phone= ? ;");
                $stmt->bind_param("s",$Phone);
               
                if($stmt->execute())
                {
                    	                $temp=array();

                   $stmt->bind_result($Firstname, $Lastname);
                   while($stmt->fetch()){
                    	//pushing fetched data in an array 
                    	$temp = [
                    		'Firstname'=>$Firstname,
                    		'Lastname'=>$Lastname
                    	];
                    	
                    	//pushing the array inside the details array 
                    	array_push($details, $temp);
                                        }
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