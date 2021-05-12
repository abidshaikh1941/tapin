<?php
require_once '../includes/addOrder.php';
$response=array();

if($_SERVER['REQUEST_METHOD']=='POST'){

    if(  isset($_POST['Weight']) and isset($_POST['Description'])
    and isset($_POST['Receiver_Name']) and isset($_POST['Receiver_Phone'])
    and isset($_POST['Receiver_Address']) and isset($_POST['Source'])
    and isset($_POST['Destination']))
    {
        $db = new AddOrder();
        $result = $db->addOrder($_POST['Weight'],
                                $_POST['Description'],
                                $_POST['Comment_Src'],
                                $_POST['Source'],
                                $_POST['Receiver_Name'],
                                $_POST['Receiver_Phone'],
                                $_POST['Receiver_Address'],
                                $_POST['Destination'],
                                $_POST['Comment_Dest'],
                                $_POST['Phone']
                                );
        
        
        if($result==2)
        {
            $response['error']=true;
            $response['message']="Order Failed";
            $response['order']=false;
        }
        elseif($result==1)
        {
            $response['error']=false;
            $response['message']="Order Successful";
            $response['order']=true;
        }
        

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