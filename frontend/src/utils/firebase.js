import firebase from 'firebase';
import { initializeApp } from "firebase/app";
const firebaseConfig = {
    apiKey: "AIzaSyB2brbb1BDWBZiwhPzTIOXtoMwlayO-T0w",
    authDomain: "forumupload-d528a.firebaseapp.com",
    projectId: "forumupload-d528a",
    storageBucket: "forumupload-d528a.appspot.com",
    messagingSenderId: "859280912038",
    appId: "1:859280912038:web:276e4a98d2e4d3a7548e67"
  };
  firebase.initializeApp(firebaseConfig);
  
  export default firebase;