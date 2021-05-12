<?php

    class GetOrder{
        private $con;
        public $details=array();
        function __construct(){
            
            require_once dirname(__FILE__).'/DbConnect.php';
            $db = new DbConnection();
            $this->con = $db->connect();
        }
        public function getOrderList($User_Id)
        {
            $stmt = $this->con->prepare("SELECT * FROM `ORDERS`  WHERE User_Id= ? ;");
           if( $stmt->bind_param("s",$User_Id))
           {
               
           }
                
                if($stmt->execute())
                {
                    $details['msg']="success_query";
                    return $details;
                }
                 else
                {
                    $details['msg']="2";
                    return $details;
                    
                }
        }
        
        public    function getOrder($Phone){
             

               $stmt = $this->con->prepare("SELECT User_Id FROM `USER_NEW`  WHERE Phone= ? ;");
                $stmt->bind_param("s",$Phone);
               
                if($stmt->execute())
                {
                    
                
                $temp=array();
                   $stmt->bind_result($User_Id);
                   $stmt->fetch();
                   $details['User_Id']=$User_Id;
                   $details['msg']="success";
                   $details=$this->getOrderList($User_Id);
                  
                
               /* if($stmt1->execute())
                {
                   // $details['order']="query executed";
                   // Order_Id,Weight,Receiver_Name,Receiver_Phone,Receiver_Address
                   
                }
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