// import {useState} from 'react';
// import storage from '../../utils/firebase';
// import firebase from '../../utils/firebase';
// function ImageUpload() {
// const [image , setImage] = useState('');
// const upload = async ()=>{
//     let id = "101";
//     const imagesRef=firebase.storage().ref(id);
//     await imagesRef.put(image);
//     imagesRef.getDownloadURL()
//     .then(url=>{

//         console.log("Image Upload Sucess", url);
//     });
// }
    
  
//   return (
//     <div className="App">
//       <center>
//       <input type="file" onChange={(e)=>{setImage(e.target.files[0])}}/>
//       <button onClick={upload}>Upload</button>
//       </center>
//     </div>
//   );
// }
  
// export default ImageUpload;