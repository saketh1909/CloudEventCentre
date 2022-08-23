import * as React from 'react';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import Button from '@mui/material/Button';
import firebase from '../../utils/firebase';
import history from '../../utils/history';
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

function Oauth() {
  const registerYourself = (e) => {
    localStorage.setItem("authType", "self");
    history.push("/register/next");
  }

  const googlesSignIn = (e) => {
    let googleProvider = new firebase.auth.GoogleAuthProvider();
    firebase
      .auth()
      .signInWithPopup(googleProvider)
      .then(response => {
        console.log(response.email, response.user.displayName);
        localStorage.setItem("authEmail", response.user.email);
        localStorage.setItem("authName", response.user.displayName);
        localStorage.setItem("authType", "google");
        history.push("/register/next");
      })
    console.log("Button Clicked");
  }
  return(
    <Grid container alignItems="center" justifyContent="center" spacing={2}>
        <Grid item xs={12} style={{textAlign: "center"}}>
          <h1> Events App </h1>
        </Grid>
        <Grid item xs={3}>
        </Grid>
        <Grid item xs={6} style={{marginBottom:"50px",textAlign:'center', border:"1px Solid"}}>
        <h1>Register</h1>
        <Box
          component="form"
          sx={{
            '& > :not(style)': { m: 1.5, width: '80%', maxWidth: '100%'},
          }}
          noValidate
          autoComplete="off"
        >
          <Button variant="contained" size="large" onClick={registerYourself}>Register Yourself</Button>
          </Box>
          <p>Else</p>
          <Button onClick={googlesSignIn} variant="contained" size="large" style={{ width:"80%"}}>Register with Google</Button>
          <p>Else</p>
          <Button onClick={()=>history.push("/login")} variant="contained" size="large" style={{marginBottom:"40px", width:"80%"}}>Login</Button>
        </Grid>
        <Grid item xs={3}>
        </Grid>
    </Grid>
  );
}

export { Oauth };
