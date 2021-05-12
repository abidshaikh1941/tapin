<?php
require_once '../includes/updateDriver.php';
$response=array();

if($_SERVER['REQUEST_METHOD']=='POST'){

    if(  isset($_POST['Phone']))
    {
        $db = new UpdateDriver();
        $result = $db->updateDriver($_POST['Firstname'],
                                $_POST['Lastname'],
                                $_POST['Address'],
                                $_POST['Gender'],
                                $_POST['Dob'],
                                $_POST['License'],
                                $_POST['Phone']
                                );
        
        if($result==1){
            $response['error']=false;
                $response['message']="Details Saved.."; 
        }
        elseif($result==2)
        {
            $response['error']=true;
            $response['message']="Some error occured";
        }
        /*elseif($result==1)
        {
            $response['error']=true;
            $response['message']="You are already registered";
        }*/
        

    }

    else{
        $response['error']=true;
        $response['message']="Required fields are missing";}
}

else{
    $response['error']=true;
    $response['message']="Invalid Request";
}

echo json_encode($response);
?>