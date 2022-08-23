import * as React from 'react';
import {Box, TextField, Grid, Button} from '@mui/material';
import axios from 'axios';
import history from "../../utils/history";
import firebase from '../../utils/firebase';
import Typography from "@mui/material/Typography";
import Modal from '@mui/material/Modal';
import { baseUrl } from '../../utils/urls';

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  p: 4,
};

function LoginAccount() {
  const [loginData, setLoginData] = React.useState({
    email : "",
    password : "",
    err:"",
    errCode:""
  }); 
  const [otp, setOtp] = React.useState();
  const [open, setOpen] = React.useState(false);
  const handleClose = () => setOpen(false);
  const validateEmail = (email) => {
    return String(email)
      .toLowerCase()
      .match(
        /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      );
  };

  const submitClicked = (e) => {
    if(loginData.email.length===0 || loginData.password.length===0 || !validateEmail(loginData.email)){
        return;
    } 
    e.preventDefault();
      axios.get(`${baseUrl}/userAuth/userSignIn?email=${loginData.email}&password=${loginData.password}&authType`)
      .then(res => {
        localStorage.setItem("userId",JSON.stringify(res.data));
        history.push('/home');
        console.log(res);
      }).catch(err => {
        console.log(err.response.status);
        if(err.response.data === "Account not validated yet. Please validate your account!"){
          setOpen(true);
        }
        setLoginData({...loginData, err: err.response.data})
      })
  };
  const googlesSignIn = (e) => {
    let googleProvider = new firebase.auth.GoogleAuthProvider();
    firebase
      .auth()
      .signInWithPopup(googleProvider)
      .then(response => {
      axios.get(`${baseUrl}/userAuth/userSignIn?email=${response.user.email}&password=""&authType=google`)
      .then(res => {
        localStorage.setItem("userId",JSON.stringify(res.data));
        history.push('/home');
        console.log(res);
      }).catch(err => {
        console.log(err.response.data);
        if(err.response.data === "Account not validated yet. Please validate your account!"){
          setOpen(true);
        }
        setLoginData({...loginData, err: err.response.data})
      })
      })
  }
  const otpValidation = (e) => {
    axios.get(`${baseUrl}/userAuth/validateUser?email=${loginData.email}&otp=${otp}`)
    .then(res => {
      console.log(res);
      localStorage.removeItem("authName");
      localStorage.removeItem("authType");
      localStorage.removeItem("authEmail");
      setOpen(false);
      history.push("/login");
    }).catch(err => {
      console.log(err.response);
      setLoginData({...loginData, err: err.response.data});
    })
  };
  return(
    <Grid container alignItems="center" justifyContent="center" spacing={2}>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
        <h1>Enter OTP</h1>
        <TextField
            id="outlined-uncontrolled"
            label="OTP"
            type="number"
            style={{width :"80%"}}
            onChange={(e) => setOtp(e.target.value)}
            value={otp}
            required
          /><br></br>
          <Button variant="contained" size="large" onClick={otpValidation}>Submit</Button>
        </Box>
      </Modal>
        <Grid item xs={12} style={{textAlign: "center"}}>
          <h1> Events App </h1>
        </Grid>
        <Grid item xs={3}>
        </Grid>
        <Grid item xs={6} style={{marginBottom:"50px",textAlign:'center', border:"1px Solid"}} >
          <h1>Sign - In</h1>
        <form>
        <Box
          component="form"
          sx={{
            '& > :not(style)': { m: 1.5, width: '80%', maxWidth: '100%'},
          }}
        >
          <TextField
            id="outlined-uncontrolled"
            label="Email"
            type="email"
            onChange={(e) => setLoginData({...loginData, email: e.target.value})}
            value={loginData.email}
            required
          />
          <TextField
            id="outlined-uncontrolled"
            label="Password"
            type="password"
            onChange={(e) => setLoginData({...loginData, password: e.target.value})}
            value={loginData.password}
            required
          />
          {loginData.err.length>0?<Typography variant="h6" color="red">
        {loginData.err}.
      </Typography>:null}
          <Button variant="contained" type="submit" onClick={(e) => submitClicked(e)} size="large">Sign In</Button>
          <h2 style={{textAlign:"center"}}>(OR)</h2>
          <Button variant="contained" onClick={(e) => googlesSignIn(e)} size="large">SignIn with Google</Button>
          <h2 style={{textAlign:"center"}}>(OR)</h2>
          <Button variant="contained" onClick={(e) => history.push("/register")} size="large" style={{marginBottom:"40px"}}>Sign Up</Button>
        </Box>
        </form>
        </Grid>
        <Grid item xs={3}>
        </Grid>
    </Grid>
  );
}

export { LoginAccount };