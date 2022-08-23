import * as React from 'react';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import axios from 'axios';
import moment from 'moment';
import history from "../../utils/history";
import Grid from '@mui/material/Grid';
import { baseUrl } from '../../utils/urls';

const locationData = {
    "New York":["Albany", "Buffalo", "New York", "Rochester"],
    "California":["San Jose","San Francisco","Los Angeles","San Diego","Sacramento","Chico"],
    "Washington":["Seattle","Bellevue","Redmond"],
    "Texas":["Austin","Dallas","Houstan","Arlington"],
    "Illinoi":["Chicago", "Springfield","Naperville"],
  }

function CreateEventForm() {
  let loggedUser = localStorage.getItem("userId");
  if(loggedUser == undefined || loggedUser === null){
    history.push("/login");
  }
  loggedUser = JSON.parse(loggedUser);
  const loggedUserId = loggedUser.id;
  const formRef = React.useRef();
  const [focus1, setFocused1] = React.useState(false);
  const [hasValue1, setHasValue1] = React.useState(false);
  const onFocus1 = () => setFocused1(true);
  const onBlur1 = () => setFocused1(false);

  const [focus2, setFocused2] = React.useState(false);
  const [hasValue2, setHasValue2] = React.useState(false);
  const onFocus2 = () => setFocused2(true);
  const onBlur2 = () => setFocused2(false);
  const [focus3, setFocused3] = React.useState(false);
  const [hasValue3, setHasValue3] = React.useState(false);
  const onFocus3 = () => setFocused3(true);
  const onBlur3 = () => setFocused3(false);
  const [eventData, setEventData] = React.useState({
    userId : loggedUserId,
    title : "",
    description : "",
    startTime : "",
    endTime : "",
    deadline : "",
    street : "",
    city : "",
    state : "",
    zip : "",
    minParticipants : "",
    maxParticipants : "",
    fees : "",
    policy : "",
    errorMessage:""
  }); 

  const submitClicked = (e,formRef) => {
        //
        if(eventData.title.length===0 || eventData.description.length===0 || eventData.startTime.length===0 || eventData.deadline.length===0 ||
            eventData.endTime.length===0 || eventData.street.length===0 || eventData.city.length===0 || eventData.state.length===0 ||
            eventData.zip.length===0 || eventData.minParticipants.length===0 || eventData.maxParticipants.length===0 || eventData.fees.length===0 ||
            eventData.policy.length===0){
                return;
            }
            e.preventDefault();
        axios.post(`${baseUrl}/event/createEvent?userId=${eventData.userId}&title=${eventData.title}&description=${eventData.description}&startTime=${moment(eventData.startTime).format('yyyy-MM-DD kk:mm:ss')}&endTime=${moment(eventData.endTime).format('yyyy-MM-DD kk:mm:ss')}&deadline=${moment(eventData.deadline).format('yyyy-MM-DD kk:mm:ss')}&street=${eventData.street}&city=${eventData.city}&state=${eventData.state}&zip=${eventData.zip}&minParticipants=${eventData.minParticipants}&maxParticipants=${eventData.maxParticipants}&fees=${eventData.fees}&policy=${eventData.policy}`)
        .then(res => {
        console.log(res);
        history.push('/home');
        }).catch(err => {
        console.log(err.response);
        })
  };
  return(
    <div style={{textAlign:"center", padding:"20px"}}>
        <Grid container spacing={2}>
            <Grid item xs={2}>
                <Button variant="contained" type="submit" onClick={(e) => history.push('/home')}  size="large">Go Back Home</Button>
            </Grid>
            <Grid item xs={8}>
                <h1>Create Event Form</h1>
            </Grid>
            <Grid item xs={2}>
            </Grid>
        </Grid>
    <form ref={formRef}>
    <Box
    component="form"
    sx={{
        '& > :not(style)': { m: 1.5, width: '80%', maxWidth: '100%'},
    }}
    
    autoComplete="off"
    >
        <TextField
            id="outlined-uncontrolled"
            label="Title"
            onChange={(e) => setEventData({...eventData, title: e.target.value})}
            value={eventData.title}
            required
        />
        <TextField
            id="outlined-uncontrolled"
            label="Description"
            onChange={(e) => setEventData({...eventData, description: e.target.value})}
            value={eventData.description}
            required
        />
        <TextField
            id="outlined-uncontrolled"
            label="Event Fee"
            type="number"
            onChange={(e) => setEventData({...eventData, fees: e.target.value})}
            value={eventData.fees}
            required
        />
        <TextField
            id="outlined-uncontrolled"
            label="Minimum Participants"
            type="number"
            onChange={(e) => setEventData({...eventData, minParticipants: e.target.value})}
            value={eventData.minParticipants}
            required
        />
        <TextField
            id="outlined-uncontrolled"
            label="Maximum Participants"
            type="number"
            onChange={(e) => setEventData({...eventData, maxParticipants: e.target.value})}
            value={eventData.maxParticipants}
            required
        />
        <TextField
            onFocus={onFocus1}
            onBlur={onBlur1}
            id="controlled"
            label="Participation Deadline"
            type={hasValue1 || focus1 ? "datetime-local" : "text"}
            onChange={(e) => setEventData({...eventData, deadline: e.target.value})}
            value={eventData.deadline}
            inputProps={{
                min: new Date().toISOString().slice(0, 16)
            }}
            required
        />
        <TextField
            onFocus={onFocus2}
            onBlur={onBlur2}
            id="controlled"
            label="Event Start Time"
            disabled = {eventData.deadline.length===0}
            type={hasValue2 || focus2 ? "datetime-local" : "text"}
            onChange={(e) => setEventData({...eventData, startTime: e.target.value})}
            inputProps={{
                min: eventData.deadline
            }}
            value={eventData.startTime}
            required
        />
        <TextField
            onFocus={onFocus3}
            onBlur={onBlur3}
            id="controlled"
            label="Event End Time"
            disabled = {eventData.deadline.length===0 || eventData.startTime.length===0}
            type={hasValue3 || focus3 ? "datetime-local" : "text"}
            onChange={(e) => setEventData({...eventData, endTime: e.target.value})}
            value={eventData.endTime}
            inputProps={{
                min: eventData.startTime
            }}
            required
        />
        
        <TextField
            id="outlined-uncontrolled"
            label="Street"
            onChange={(e) => setEventData({...eventData, street: e.target.value})}
            value={eventData.street}
            required
        />
        <TextField
            id="outlined-uncontrolled"
            label="Zip Code"
            typle="number"
            onChange={(e) => setEventData({...eventData, zip: e.target.value})}
            value={eventData.zip}
            required
        />


        <FormControl fullWidth>
            <InputLabel id="demo-simple-select-label">State</InputLabel>
            <Select
                labelId="demo-simple-select-label"
                id="demo-simple-select"
                label="State"
                onChange={(e) => setEventData({...eventData, state: e.target.value})}
                value={eventData.state}
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
                onChange={(e) => setEventData({...eventData, city: e.target.value})}
                value={eventData.city}
                required
            >
                {(eventData.state === null || eventData.state === undefined || eventData.state.length===0)? null: (locationData[eventData.state]).map((loc)=>
                <MenuItem value={loc}>{loc}</MenuItem>
                )}
            </Select>
        </FormControl>
        <FormControl fullWidth>
            <InputLabel id="demo-simple-select-label">Admission Policy</InputLabel>
            <Select
            labelId="demo-simple-select-label"
            id="demo-simple-select"
            label="Gender"
            onChange={(e) => setEventData({...eventData, policy: e.target.value})}
            value={eventData.policy}
            required
            >
            <MenuItem value={"FCFS"}>First Come First Serve</MenuItem>
            <MenuItem value={"AR"}>Approval Required</MenuItem>
            </Select>
        </FormControl>
        <Button variant="contained" type="submit" onClick={(e) => {formRef.current.reportValidity();submitClicked(e,formRef.current);}}  size="large" style={{marginBottom:"40px"}}>Create Event</Button>
    </Box>
    </form>
    </div>
  );
}

export { CreateEventForm };
