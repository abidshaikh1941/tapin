<?php
require_once '../includes/operations.php';
$response=array();

if($_SERVER['REQUEST_METHOD']=='POST'){

    if(  isset($_POST['Phone']) and isset($_POST['Fb_Id'])  )
    {
        $db = new DbOperation();
        $result = $db->createDriver($_POST['Phone'],
                                $_POST['Fb_Id']
                                );
        
        if($result==0){
            $response['error']=false;
                $response['message']="exists"; 
        }
        elseif($result==2)
        {
            $response['error']=true;
            $response['message']="Some error occured";
        }
        elseif($result==1)
        {
            $response['error']=true;
            $response['message']="registered";
        }
        /*if(
        $db->createUser($_POST['username'],
        $_POST['password'],
        $_POST['email']))
        {
            
                $response['error']=false;
                $response['message']="User Created Succesfully";  
        }
        else {
                $response['error']=true;
                $response['message']="User not created...";
        }*/

    }

    else{
        $response['error']=true;
        $response['message']="Required fields are missing";
        
    }
}

else{
    $response['error']=true;
    $response['message']="Invalid Request";
}

echo json_encode($response);
?>