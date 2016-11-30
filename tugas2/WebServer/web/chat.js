window.addEventListener("load",function(){
    var test = angular.module('ChatBox', []);
    
    window.atest = test;

    test.controller('chatController', function($scope) {
	$scope.messages = [];
        
        $scope.chat_with = "nobody";
        $scope.hideChat = true;
        
        
        var msg_ids = {}; 
        var saleProjectChat = window.saleProjectChat;
        saleProjectChat.displayMessage = function(key,from,to,text){
            if( !msg_ids[key] ){
                msg_ids[key] = 1;
                $scope.$apply(function(){
                    var nmsg = {
                        msg : text.replace(/\n/g, '<br>'),
                        status : ( from == cusername ? 'sent' : 'received' )
                    };
                    $scope.messages.push(nmsg);
                    //   this.messageList.scrollTop = this.messageList.scrollHeight;
                    //   this.messageInput.focus();
                });
            }
        };
        
        saleProjectChat.notifFn = function(from,to){
            $scope.startChat(to);
        }

	$scope.sendMessage = function(e) {
            if (document.getElementById("newmsg").value != "") {
/*                $scope.messages.push({
                    msg: $scope.newmsg, 
                    status: 'sent'
                });
*/                
                saleProjectChat.saveMessage($scope.newmsg);
            }
            document.getElementById("newmsg").value = "";
	};
        
        $scope.startChat = function(target){
            if( target == $scope.chat_with ) return;
            $scope.hideChat = false;
            
            $scope.messages = [];
            $scope.chat_with = target;
            console.log("Start Chat With " + target);

            saleProjectChat.changeTarget(target);                
        }
        
        $scope.closeChat = function(){
            $scope.chat_with = "nobody";
            $scope.hideChat = true;
        }
        
        
    });
});
