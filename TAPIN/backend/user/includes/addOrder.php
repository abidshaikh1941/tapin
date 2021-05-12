<?php

    class AddOrder{
        private $con;
        function __construct(){
            
            require_once dirname(__FILE__).'/DbConnect.php';
            $db = new DbConnection();
            $this->con = $db->connect();
        }

        public    function addOrder($Weight,$Description,$Comment_Src,$Source,$Receiver_Name,$Receiver_Phone,$Receiver_Address,$Destination,$Comment_Dest,$Phone){
                $User_Id=6;
               $stmtp = $this->con->prepare("SELECT User_Id FROM USER_NEW WHERE Phone=?;");
               $stmtp->bind_param("s",$Phone);
                if($stmtp->execute())
                {
                    $result=$stmtp->get_result();
                    $row=$result->fetch_assoc();
                    $User_Id = $row['User_Id'];
                     
                                         //$User_Id = "8";

                }
                else
                {
                    $User_Id = "6";
                }
               $stmt = $this->con->prepare("INSERT INTO ORDERS (Weight,Description,Comment_Src,Source,Receiver_Name,Receiver_Phone,Receiver_Address,Destination,Comment_Dest,User_Id) VALUES (?,?,?,?,?,?,?,?,?,?);");
                $stmt->bind_param("ssssssssss",$Weight,$Description,$Comment_Src,$Source,$Receiver_Name,$Receiver_Phone,$Receiver_Address,$Destination,$Comment_Dest,$User_Id);
               
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

?>