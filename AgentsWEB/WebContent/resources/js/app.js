var app = angular.module('myApp', []);


app.controller('myController', function($scope,$rootScope,$location,$http){
	
	$scope.showTypes = false;
	$scope.showAgents = false;
	$scope.showPerformative = false;
	$scope.showSendMessage = false;
	
	$scope.senderClass = "sender";
	$scope.reciverClass = "reciver";
	$scope.replyClass = "reply";
	
	$scope.sendVia = "rest";
	
	
	// socket

	
	var url = window.location;
	var wslocation = "ws://"+url.hostname+":"+url.port+"/AgentsWEB/socketService";
	
	socket = new WebSocket(wslocation);
	
	socket.onclose = function(){
		socket.close();
	}
	
	socket.onmessage = function(msg){
		var message = JSON.parse(msg.data);
		
		switch (message.type) {
		case "SEND_ACLMESSAGE":
			alert(message.info)
			break;
		case "ACTIVE_AGENT":
			alert(message.info);
			$scope.getAgents();
			break;
		case "GET_TYPES":
			$scope.agentTypes = message.agentTypes;
			break;
		case "GET_AGENTS":
			$scope.activeAgents = message.activeAgents;
			break;
		case "GET_PERFROMATIVE":
			$scope.performatives = message.perfomatives;
			break;
		case "STOP_AGENT":
			alert(message.info);
			$scope.getAgents();
			break;

		default:
			alert("Wrong Message type.")
			break;
		}
		
	}	
	
	
	// ===============
	
	$scope.newAgent = {
			name: "",
			type: ""
	}
	
	$scope.ACLMessage = {
			sender: "",
			receivers: [],
			content: "",
			performative: "",
			replyTo: ""
	}
	
	$scope.typeList = [];
	
	$scope.sendMessage = function(){
		showTable(false, false, false, true);
		
		$scope.ACLMessage.sender = "";
		$scope.ACLMessage.receivers = [];
		$scope.ACLMessage.content = "";
		$scope.ACLMessage.performative = "";
		$scope.ACLMessage.replyTo == "";
		
		
		if($scope.sendVia == "rest"){
			
			$http({
				method: 'GET',
				url: "/AgentsWEB/rest/klient/activeAgents"
			}).then(
				function success(response){
					$scope.activeAgents = response.data;
				},
				function error(){
					alert("Error!");
				}
			);
			
			$http({
				method: 'GET',
				url: "/AgentsWEB/rest/klient/performative"
			}).then(
				function success(response){
					$scope.performatives = response.data;
				},
				function error(){
					alert("Error!");
				}
			);
		
		}else if($scope.sendVia == "socket"){
			var socketMessage = {
						  type: ""			
			}
			
			socketMessage.type = "GET_PERFROMATIVE";
			socket.send(JSON.stringify(socketMessage));

			socketMessage.type = "GET_AGENTS";
			socket.send(JSON.stringify(socketMessage));
			
		}
		
	}
	
	$scope.sendACLMessage = function(){
		
		if($scope.ACLMessage.sender == null || $scope.ACLMessage.sender == "" 
		  || $scope.ACLMessage.receivers.length == 0 
		  || $scope.ACLMessage.performative == "" 
		  || $scope.ACLMessage.replyTo == ""){
			
			alert("Invalid input!");
			return;
		}
		
		
		if($scope.sendVia == "rest"){
			
			$http({
				method: 'POST',
				data: JSON.stringify($scope.ACLMessage),
				headers: {'Content-Type': 'application/json'},
				url: "/AgentsWEB/rest/klient/send"
			}).then(
				function success(response){
					alert(response.data);				
				},
				function error(){
					alert("Error!");
				}
			);	
		}else if($scope.sendVia == "socket"){
			
			var socketMessage = {
					aclMessage: $scope.ACLMessage,
						  type: ""			
			}
			
			socketMessage.aclMessage = $scope.ACLMessage;
			socketMessage.type = "SEND_ACLMESSAGE";
			
			socket.send(JSON.stringify(socketMessage));
			
		}
		
	}
	
	
	
	$scope.activeAgent = function(){
			
		if($scope.newAgent.name.trim() == "" || $scope.newAgent.type == ""){
			alert("Wrong input!");
			return;
		}
		
		if($scope.sendVia == "rest"){
			$http({
				method: 'PUT',
				url: "/AgentsWEB/rest/klient/run/"+$scope.newAgent.type+"/"+$scope.newAgent.name
			}).then(
				function success(response){
					alert(response.data);
				},
				function error(){
					alert("Error!");
				}
			);
		}else if($scope.sendVia == "socket"){
			
			var socketMessage = {
					activeAgent: $scope.newAgent,
						  type: "ACTIVE_AGENT"			
			}
			
			socket.send(JSON.stringify(socketMessage));
			
		}
		
	}
	
	
	$scope.getTypes = function(){
		showTable(true, false, false, false);
		
		if($scope.sendVia == "rest"){
			$http({
				method: 'GET',
				url: "/AgentsWEB/rest/klient/agentTypes"
			}).then(
				function success(response){
					$scope.agentTypes = response.data;
				},
				function error(){
					alert("Error!");
				}
			);
		}else if($scope.sendVia == "socket"){
			var socketMessage = {
						  type: "GET_TYPES"			
			}

			socket.send(JSON.stringify(socketMessage));
			
		}
	}
	
	$scope.getAgents = function(){
		showTable(false, true, false, false);
		
		if($scope.sendVia == "rest"){
			$http({
				method: 'GET',
				url: "/AgentsWEB/rest/klient/activeAgents"
			}).then(
				function success(response){
					$scope.activeAgents = response.data;
				},
				function error(){
					alert("Error!");
				}
			);
		}else if($scope.sendVia == "socket"){
			var socketMessage = {
					  type: "GET_AGENTS"			
			}
			
			socket.send(JSON.stringify(socketMessage));
			
		}
	}
	
	$scope.getPerformative = function(){
		showTable(false, false, true, false);
		
		
		if($scope.sendVia == "rest"){
		$http({
			method: 'GET',
			url: "/AgentsWEB/rest/klient/performative"
		}).then(
			function success(response){
				$scope.performatives = response.data;
			},
			function error(){
				alert("Error!");
			}
		);
		}else if($scope.sendVia == "socket"){
			var socketMessage = {
					  type: "GET_PERFROMATIVE"			
			}
			
			socket.send(JSON.stringify(socketMessage));
			
		}
	}
	
	
	$scope.stopAgent = function(agent){
		
		if($scope.sendVia == "rest"){
			$http({
				method: 'DELETE',
				data: JSON.stringify(agent.id),
				headers: {'Content-Type': 'application/json'},
				url: "/AgentsWEB/rest/klient/stop"
			}).then(
				function success(response){
					alert(response.data);				
				},
				function error(){
					alert("Error!");
				}
			);		
		}else if($scope.sendVia == "socket"){
			var socketMessage = {
					  stopAgentAID: agent.id,
					  type: "STOP_AGENT"			
			}
			
			socket.send(JSON.stringify(socketMessage));
			
		}
	}
	
	
	//pomocne funkcije
	var showTable = function(types, agents, performative, message){
		$scope.showTypes = types;
		$scope.showAgents = agents;
		$scope.showPerformative = performative;
		$scope.showSendMessage = message;
	}
	
	
	$http({
		method: 'GET',
		url: "/AgentsWEB/rest/klient/agentTypes"
	}).then(
		function success(response){
			
			for(var centerAddress in response.data){
				
				for(var type in response.data[centerAddress]){
					$scope.typeList.push(response.data[centerAddress][type].name + "_" + centerAddress);
				}
				
			}
			
		},
		function error(){
			alert("Error!");
		}
	);
	
	$scope.addRemoveReciver = function(agent){
		
		if($scope.ACLMessage.sender == agent.id || $scope.ACLMessage.replyTo == agent.id){
			alert("Agent is selected as sender or as recipient!");
			return;
		}
		
		if($scope.ACLMessage.receivers.indexOf(agent.id) == -1){
			$scope.ACLMessage.receivers.push(agent.id);
		}else{
			$scope.ACLMessage.receivers.splice($scope.ACLMessage.receivers.indexOf(agent.id), 1);
		}
		
	}
	
	$scope.makeMeSender = function(agent){
		
		if($scope.ACLMessage.receivers.indexOf(agent.id) == -1
				&& $scope.ACLMessage.replyTo != agent.id){
			$scope.ACLMessage.sender = agent.id;
		}else{
			alert("Agent is in reciver list or selected as recipient!")
		}
		
	}
	
	$scope.replyToMe = function(agent){
		
		if($scope.ACLMessage.receivers.indexOf(agent.id) == -1 
				&& $scope.ACLMessage.sender != agent.id){
			
			$scope.ACLMessage.replyTo = agent.id;
			
		}else{
			alert("Agent is in reciver list or selected as sender!")
		}
		
	}
	
	$scope.setRowClass = function(agent){
		
		if(agent.id == $scope.ACLMessage.sender){
			return $scope.senderClass;
		}else if($scope.ACLMessage.receivers.indexOf(agent.id) != -1){
			return $scope.reciverClass;
		}else if($scope.ACLMessage.replyTo == agent.id){
			return $scope.replyClass;
		}
		
	
		
	}
	
});










