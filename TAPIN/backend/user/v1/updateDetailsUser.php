<?php
require_once '../includes/updateUser.php';
$response=array();
/*$target_dir="/upload/images";
if(!file_exists($target_dir))
{
    mkdir($target_dir,0777,true);
}*/
if($_SERVER['REQUEST_METHOD']=='POST'){

    if(  isset($_POST['Phone']))
    {
        $db = new UpdateUser();
        $result = $db->updateUser($_POST['Firstname'],
                                $_POST['Lastname'],
                                $_POST['Address'],
                                $_POST['Gender'],
                                $_POST['Dob'],
                                $_POST['Phone']
                                );
        /*$Profile=$_POST['Profile'];
        $target_dir = $target_dir . "/" .$_POST['Phone']."jpeg";
        if(file_put_contents($target_dir,base64_decode($Profile)))
        {
            $response['Profile']="uploaded";
        }*/
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