import * as React from 'react';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import TextareaAutosize from '@mui/material/TextareaAutosize';
import Select from '@mui/material/Select';
import Button from '@mui/material/Button';
import axios from 'axios';
import history from '../../utils/history';
import Modal from '@mui/material/Modal';
import Typography from "@mui/material/Typography";
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
const locationData = {
  "New York":["Albany", "Buffalo", "New York", "Rochester"],
  "California":["San Jose","San Francisco","Los Angeles","San Diego","Sacramento","Chico"],
  "Washington":["Seattle","Bellevue","Redmond"],
  "Texas":["Austin","Dallas","Houstan","Arlington"],
  "Illinoi":["Chicago", "Springfield","Naperville"],
}
function RegisterAccount() {
  const formRef = React.useRef();
  if(localStorage.getItem("authType")===null || localStorage.getItem("authType")===undefined){
    history.push("../register");
  }
  const validateEmail = (email) => {
    return String(email)
      .toLowerCase()
      .match(
        /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      );
  };
  const [registerData, setRegisterData] = React.useState({
    name : localStorage.getItem("authName"),
    email : localStorage.getItem("authEmail"),
    password : "",
    accountType : "",
    screenName : "",
    gender : "",
    state : "",
    city: "",
    zip: "",
    street :  "",
    description : "",
    err:""
  }); 
  const [otp, setOtp] = React.useState();
  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const submitClicked = (e,formRef) => {
    
    if(registerData.email.length===0 || registerData.password.length===0 || registerData.screenName.length===0 || !validateEmail(registerData.email)){
      return;
    } 
    e.preventDefault();
    axios.post(`${baseUrl}/userAuth/userSignUp?name=${registerData.name}&email=${registerData.email}&password=${registerData.password}&accountType=${registerData.accountType}&screenName=${registerData.screenName}&gender=${registerData.gender}&state=${registerData.state}&city=${registerData.city}&zip=${registerData.zip}&street=${registerData.street}&description=${registerData.description}`)
    .then(res => {
      setOpen(true);
      console.log(res);
    }).catch(err => {
      console.log(err.response);
      setRegisterData({...registerData, err: err.response.data});
    })
  };

  const otpValidation = (e) => {
    axios.get(`${baseUrl}/userAuth/validateUser?email=${registerData.email}&otp=${otp}`)
    .then(res => {
      console.log(res);
      localStorage.removeItem("authName");
      localStorage.removeItem("authType");
      localStorage.removeItem("authEmail");
      setOpen(false);
      history.push("/login");
    }).catch(err => {
      console.log(err.response);
      setRegisterData({...registerData, err: err.response.data});
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
        <Grid item xs={6} style={{marginBottom:"50px",textAlign:'center', border:"1px Solid"}}>
        <h1>Register</h1>
        <form ref={formRef}>
        <Box
          component="form"
          sx={{
            '& > :not(style)': { m: 1.5, width: '80%', maxWidth: '100%'},
          }}
          noValidate
          autoComplete="off"
        >
          <FormControl fullWidth>
            <InputLabel id="demo-simple-select-label">Account Type</InputLabel>
            <Select
              labelId="demo-simple-select-label"
              id="demo-simple-select"
              label="Age"
              onChange={(e) => setRegisterData({...registerData, accountType: e.target.value})}
              value={registerData.accountType}
              required
            >
              <MenuItem value={"P"}>Person</MenuItem>
              <MenuItem value={"O"}>Organization</MenuItem>
            </Select>
          </FormControl>
          { localStorage.getItem("authType") !== "google" ? <>
          <TextField
            id="outlined-name"
            label="Name"
            onChange={(e) => setRegisterData({...registerData, name: e.target.value})}
            value={registerData.name}
            required
          />
          <TextField
            id="outlined-uncontrolled"
            label="Email"
            type='email'
            onChange={(e) => setRegisterData({...registerData, email: e.target.value})}
            value={registerData.email}
            required
          />
          </>: null }
          <TextField
            id="outlined-uncontrolled"
            label="Password"
            type="password"
            onChange={(e) => setRegisterData({...registerData, password: e.target.value})}
            value={registerData.password}
            required
          />
          
          <TextField
          id="outlined-uncontrolled"
          label="ScreenName"
          onChange={(e) => setRegisterData({...registerData, screenName: e.target.value})}
          value={registerData.screenName}
          required
          />
          { registerData.accountType === "P" ? <>
            <FormControl fullWidth>
              <InputLabel id="demo-simple-select-label">Gender</InputLabel>
              <Select
                labelId="demo-simple-select-label"
                id="demo-simple-select"
                label="Gender"
                onChange={(e) => setRegisterData({...registerData, gender: e.target.value})}
                value={registerData.gender}
                required
              >
                <MenuItem value={"M"}>Male</MenuItem>
                <MenuItem value={"F"}>Female</MenuItem>
                <MenuItem value={"O"}>Other</MenuItem>
              </Select>
            </FormControl></>
          : null }

          <TextField
            id="outlined-uncontrolled"
            label="Street"
            onChange={(e) => setRegisterData({...registerData, street: e.target.value})}
            value={registerData.street}
            required
          />          
          <FormControl fullWidth>
            <InputLabel id="demo-simple-select-label">State</InputLabel>
            <Select
              labelId="demo-simple-select-label"
              id="demo-simple-select"
              label="State"
              onChange={(e) => setRegisterData({...registerData, state: e.target.value})}
              value={registerData.state}
              required
            >
              {Object.keys(locationData).map((loc)=>
                <MenuItem value={loc}>{loc}</MenuItem>
              )}
            </Select>
          </FormControl>
          <FormControl fullWidth>
            <InputLabel id="demo-simple-select-label">City</InputLabel>
            <Select
              labelId="demo-simple-select-label"
              id="demo-simple-select"
              label="City"
              onChange={(e) => setRegisterData({...registerData, city: e.target.value})}
              value={registerData.city}
              required
            >
              {(registerData.state === null || registerData.state === undefined || registerData.state.length===0)? null: (locationData[registerData.state]).map((loc)=>
              <MenuItem value={loc}>{loc}</MenuItem>
              )}
            </Select>
          </FormControl>
          <TextField
          id="outlined-uncontrolled"
          label="Zip Code"
          onChange={(e) => setRegisterData({...registerData, zip: e.target.value})}
          value={registerData.zip}
          required
          />
          <TextareaAutosize
            aria-label="Description"
            minRows={3}
            placeholder="Please Enter a Description"
            style={{ width: "80%"  }}
            onChange={(e) => setRegisterData({...registerData, description: e.target.value})}
            value={registerData.description}
            required
          />
          {registerData.err.length>0?<Typography variant="h6" color="red">
          {registerData.err}.
        </Typography>:null}
          <Button variant="contained" type="submit" size="large" onClick={(e) => {formRef.current.reportValidity();submitClicked(e,formRef.current);}}>Register</Button>
          </Box>
          </form>
          </Grid>
        <Grid item xs={3}>
        </Grid>
    </Grid>
  );
}

export { RegisterAccount };
