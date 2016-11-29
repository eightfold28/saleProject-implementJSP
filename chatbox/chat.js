var test = angular.module('ChatBox', []);

test.controller('chatController', function($scope) {
	$scope.messages = [
					{msg: 'hello', status: 'received'},
					{msg: 'hi', status: 'sent'},
					{msg: 'how are you?', status: 'received'}
					];

	$scope.sendMessage = function() {
		if (document.getElementById("newmsg").value != "") {
			$scope.messages.push({
				msg: $scope.newmsg, 
				status: 'sent'
			});
		}
		document.getElementById("newmsg").value = "";
	};
});