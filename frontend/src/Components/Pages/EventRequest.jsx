import * as React from 'react';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import Button from '@mui/material/Button';
import { EventCard } from '../Common/EventCard';
import { CreateEventForm } from './CreateEventForm';
import axios from 'axios';
import { ApprovalCard } from '../Common/ApprovalCard';
import {useParams} from 'react-router-dom';
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

function EventRequest() {
  const {eventId} = useParams();
  let loggedUser = localStorage.getItem("userId");
  if(loggedUser == undefined || loggedUser === null){
    history.push("/login");
  }
  const [eventReqData, setEventReqData] = React.useState();
  React.useEffect(() => {
    axios.get(`${baseUrl}/event/getEventApprovalRequests?eventId=${eventId}`)
        .then(res => {
        console.log("dfdsfaf",res.data);
        setEventReqData(res.data);
        }).catch(err => {
        console.log(err.response);
        })
  },[]);

  return(
    <Box sx={{ flexGrow: 1 }}>
      <Grid container spacing={2}>
        <Grid item xs={2}>
            <Button variant="contained" type="submit" onClick={(e) => history.push('/home')}  size="large">Go Back</Button>
        </Grid>
        <Grid item xs={8}>
        </Grid>
        <Grid item xs={2}>
        </Grid>
        <Grid item xs={8}>
            <h1 style={{margin:"0px"}}>RSVP Request</h1>
        </Grid>
        <Grid item xs={4}>
        </Grid>
        {(eventReqData != undefined)? eventReqData.map(req => (
            <Grid item xs={4} lg={3} key={req.id}>
              <ApprovalCard data = {req}/>
            </Grid>
          )): " " }
      </Grid>
    </Box>
  );
}

export { EventRequest };
