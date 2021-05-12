<?php

    class UpdateDriver{
        private $con;
        function __construct(){
            
            require_once dirname(__FILE__).'/DbConnect.php';
            $db = new DbConnection();
            $this->con = $db->connect();
        }

        public    function updateDriver($Firstname,$Lastname,$Address,$Gender,$Dob,$License,$Phone){
             

               $stmt = $this->con->prepare("UPDATE `DRIVER_NEW` SET `Firstname`= ? ,`Lastname`= ? ,`Address`= ? ,
               `Gender`=?,`Dob`=? ,`License`=? WHERE Phone= ? ;");
                $stmt->bind_param("sssssss",$Firstname,$Lastname,$Address,$Gender,$Dob,$License,$Phone);
               
                if($stmt->execute())
                {
                    return 1;
                }
                else
                {
                    return 2;
                }
            
        }
        
        /*private function isUserExists($Phone,$Fb_Id)
        {
            $stmt = $this->con->prepare("SELECT id FROM user_new WHERE Fb_Id=? OR Phone=?");
            $stmt->bind_param("ss",$Phone,$Fb_Id);
            $stmt->execute();
            $stmt->store_result();
            return $stmt->num_rows()>0;
        }*/
    }

?>