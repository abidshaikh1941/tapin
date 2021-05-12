<?php
require_once '../includes/getUser.php';
$response=array();
$result=array();
if($_SERVER['REQUEST_METHOD']=='POST'){

    if(  isset($_POST['Phone']))
    {
        $db = new GetUser();
        $result = $db->getUser(
                                $_POST['Phone']
                                );
        
        
        if($result['msg']=="2")
        {
            $response['error']=true;
            $response['message']="Fail to get data";
        }
        else
        {
            $response['error']=false;
         
        }
       
        

    }

    else{
        $response['error']=true;
        $response['message']="Required fields are missing";}
}

else{
    $response['error']=true;
    $response['message']="Invalid Request";
}

echo json_encode($result);
?>