import * as React from 'react';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import { UserEventCard } from '../Common/UserEventCard';
import axios from 'axios';
import { RegisteredEventCard } from '../Common/RegisteredEventCard';
import { baseUrl } from '../../utils/urls';
import history from '../../utils/history';

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

function UserEvents() {
  const [userCreatedData, setUserCreatedData] = React.useState();
  const [userRegisteredData, setUserRegisteredData] = React.useState();
  let loggedUser = localStorage.getItem("userId");
  if(loggedUser == undefined || loggedUser === null){
    history.push("/login");
  }
  let loggedUserId = localStorage.getItem("userId");
  loggedUserId = JSON.parse(loggedUserId);
  console.log(loggedUserId.id);
  loggedUserId = loggedUserId.id;
  React.useEffect(() => {
    axios.get(`${baseUrl}/event/getEventsCreatedByUser?userId=${loggedUserId}`)
        .then(res => {
        console.log(res.data);
        if(res.data != undefined){
          setUserCreatedData(res.data);
        }
        }).catch(err => {
        console.log(err.response);
    })
    axios.get(`${baseUrl}/event/getEventsRegisteredByUser?userId=${loggedUserId}`)
        .then(res => {
        console.log(res.data);
        if(res.data != undefined){
          setUserRegisteredData(res.data);
        }
        }).catch(err => {
        console.log(err.response);
    })
  },[]);
  return(
    <Box sx={{ flexGrow: 1 }}>
      <Grid container spacing={2}>
        {/* <Grid item xs={12}>
        </Grid>
        <Grid item xs={4}>
        </Grid> */}
        
        <Grid item xs={12}>
          <h1>User Created Events</h1>
        </Grid>
        {(userCreatedData != undefined)? userCreatedData.map(cd => (
        <Grid item xs={4}>
            <UserEventCard data={cd}/>
        </Grid>
        )): " " }
        <Grid item xs={12}>
          <h1>User Registered Events</h1>
        </Grid>
        {(userRegisteredData != undefined)? userRegisteredData.map(rd => (
        <Grid item xs={4}>
            <RegisteredEventCard data={rd}/>
        </Grid>
        )): " " }
      </Grid>
    </Box>
  );
}

export { UserEvents };
