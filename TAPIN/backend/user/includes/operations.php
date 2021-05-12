<?php

    class DbOperation{
        private $con;
        function __construct(){
            
            require_once dirname(__FILE__).'/DbConnect.php';
            $db = new DbConnection();
            $this->con = $db->connect();
        }

        public    function createUser($Phone,$Fb_Id){
            if($this->isUserExists($Phone,$Fb_Id)){
                return 0;
            }  
            else{ 
               $stmt = $this->con->prepare("INSERT INTO USER_NEW (Phone, Fb_Id) VALUES (?,?);");
                $stmt->bind_param("ss",$Phone,$Fb_Id);
               
                if($stmt->execute())
                {
                    return 1;
                }
                else
                {
                    return 2;
                }
            }
        }
        
        private function isUserExists($Phone,$Fb_Id)
        {
            $stmt = $this->con->prepare("SELECT User_Id FROM USER_NEW WHERE Phone=? OR Fb_Id=? ;");
            $stmt->bind_param("ss",$Phone,$Fb_Id);
            $stmt->execute();
            $stmt->store_result();
            return $stmt->num_rows()>0;
        }
    }

?>