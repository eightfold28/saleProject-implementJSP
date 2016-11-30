/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
'use strict';

// Initializes SaleProjectChat.
function SaleProjectChat() {
  this.checkSetup();
  // Saves message on form submit.
//  this.messageForm.addEventListener('submit', this.saveMessage.bind(this));
  // this.signOutButton.addEventListener('click', this.signOut.bind(this));
//  this.signInButton.addEventListener('click', this.signIn.bind(this));

  // Toggle for the button.

    this.initFirebase();
    this.initNotif();
    this.signIn();
    
  

    // // Test
    // this.testForm = document.getElementById('test-form');
    // this.testForm.addEventListener('submit', this.submitTest.bind(this));
}

SaleProjectChat.prototype.changeTarget = function(new_target){
    this.ntardeh(cusername, new_target);    
}

// SaleProjectChat.prototype.submitTest = function () {
//   var name = document.getElementById('test').value;
//   $.post({
//     url: 'http://localhost:8080/ChatService/test',
//     data: {
//       name: name
//     }
//   })
//   .done(function (data) {
//     alert(JSON.stringify(data));
//   })
//   .fail(function () {
//     console.log('error');
//   });
// }

SaleProjectChat.prototype.ntardeh = function (from,to) {
  this.from = from;
  this.to = to;

  var ref = 'conversations/' + ((from < to) ? (from + '-' + to) : (to + '-' + from));
  console.log(ref);
  this.messagesRef = this.database.ref(ref);
  // Make sure we remove all previous listeners.
  this.messagesRef.off();

  var setMessage = function(data) {
    var val = data.val();
    console.log(val);
    this.displayMessage(data.key, val.from, val.to, val.message);
  }.bind(this);
  
  this.messagesRef.on('child_added', setMessage);
  this.messagesRef.on('child_changed', setMessage);
};

SaleProjectChat.prototype.notifFn = function(){ console.log(arguments); };

// Sets up shortcuts to Firebase features and initiate firebase auth.
SaleProjectChat.prototype.initFirebase = function() {
  // TODO(DEVELOPER): Initialize Firebase.
  // Shortcuts to Firebase SDK features.
  this.auth = firebase.auth();
  this.database = firebase.database();
  this.storage = firebase.storage();

    // Initiates Firebase auth and listen to auth state changes.
  this.auth.onAuthStateChanged(this.onAuthStateChanged.bind(this));
};

SaleProjectChat.prototype.initNotif = function(){
  this.notifRef = this.database.ref("conversation_notif");
  var notif = function(data){
      var val = data.val();
      var key = data.getKey();
      console.log(data);
      console.log(key);
      console.log(val);
      if( key == cusername )
        this.notifFn( cusername, val.from );
  }.bind(this);
  this.notifRef.on('child_changed', notif);
  this.notifRef.on('child_added', notif);
        
}

SaleProjectChat.prototype.onAuthStateChanged = function(){
    
}

// Saves a new message on the Firebase DB.
SaleProjectChat.prototype.saveMessage = function(message) {
  // Check that the user entered a message and is signed in.

    // TODO(DEVELOPER): push new message to Firebase.
    // var currentUser = this.auth.currentUser;
    // Add a new message entry to the Firebase Database.
    // this.messagesRef.push({
    //   from: this.from,
    //   to: this.to,
    //   message: this.messageInput.value
    // }).then(function() {
    //   // Clear message text field and SEND button state.
    //   SaleProjectChat.resetMaterialTextfield(this.messageInput);
    //   this.toggleButton();
    // }.bind(this)).catch(function(error) {
    //   console.error('Error writing new message to Firebase Database', error);
    // });

    var from = this.from;
    var to = this.to;
  $.post({
    url: 'http://localhost:8080/ChatService/test',
    data: {
      from: from,
      to: to,
      message: message
    }
  })
  .done(function (data) {
    //alert(JSON.stringify(data));
    //  Clear message text field and SEND button state.
  })
  .fail(function () {
    console.log('error');
  });

};

SaleProjectChat.prototype.signIn = function () {
  var from = this.from;

  $.post({
    url: 'http://localhost:8080/ChatService/signin',
    data: {
      username: from,
      token: token // TODO: ganti sama token beneran
    }
  })
  .done(function (data) {
    console.log('custom token: ', data.token);
    //alert(JSON.stringify(data.token));
    firebase.auth().signInWithCustomToken(data.token).catch(function(error) {
      // Handle Errors here.
      var errorCode = error.code;
      var errorMessage = error.message;
      console.log('error signin in with custom token: ', errorMessage);
    });
  })
  .catch(function () {
    console.log('error getting custom token');
  });
};


// Displays a Message in the UI.
SaleProjectChat.prototype.displayMessage = function(key, from, to, text) {
  var div = document.getElementById(key);
  // If an element for that message does not exists yet we create it.
  if (!div) {
    var container = document.createElement('div');
    container.innerHTML = SaleProjectChat.MESSAGE_TEMPLATE;
    div = container.firstChild;
    div.setAttribute('id', key);
    this.messageList.appendChild(div);
  }
  div.querySelector('.name').textContent = from;
  var messageElement = div.querySelector('.message');
  if (text) { // If the message is text.
    messageElement.textContent = text;
    // Replace all line breaks by <br>.
    messageElement.innerHTML = messageElement.innerHTML.replace(/\n/g, '<br>');
  }
  // Show the card fading-in.
  setTimeout(function() {div.classList.add('visible')}, 1);
  this.messageList.scrollTop = this.messageList.scrollHeight;
  this.messageInput.focus();
};

// Enables or disables the submit button depending on the values of the input
// fields.
SaleProjectChat.prototype.toggleButton = function() {
  if (this.messageInput.value) {
    this.submitButton.removeAttribute('disabled');
  } else {
    this.submitButton.setAttribute('disabled', 'true');
  }
};

// Checks that the Firebase SDK has been correctly setup and configured.
SaleProjectChat.prototype.checkSetup = function() {
  if (!window.firebase || !(firebase.app instanceof Function) || !window.config) {
    window.alert('You have not configured and imported the Firebase SDK. ' +
        'Make sure you go through the codelab setup instructions.');
  } else if (config.storageBucket === '') {
    window.alert('Your Firebase Storage bucket has not been enabled. Sorry about that. This is ' +
        'actually a Firebase bug that occurs rarely. ' +
        'Please go and re-generate the Firebase initialisation snippet (step 4 of the codelab) ' +
        'and make sure the storageBucket attribute is not empty. ' +
        'You may also need to visit the Storage tab and paste the name of your bucket which is ' +
        'displayed there.');
  }
};
window.addEventListener("load",function(){
    window.saleProjectChat = new SaleProjectChat();
    
})

