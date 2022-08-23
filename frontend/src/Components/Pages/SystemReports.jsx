import * as React from 'react';
import axios from 'axios';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import { ReviewCard } from '../Common/ReviewCard';  
import history from '../../utils/history';
import { baseUrl } from '../../utils/urls';
import Button from '@mui/material/Button';
  
function SystemReports(props) {
  let loggedUser = localStorage.getItem("userId");
  if(loggedUser == undefined || loggedUser === null){
    history.push("/login");
  }
  loggedUser = JSON.parse(loggedUser);
  const loggedUserId = loggedUser.id;
  const accountType = loggedUser.accountType;
  const [systemReport, setSystemReports] = React.useState();
  const [userReports, setUserReports] = React.useState();
  React.useEffect(() => {
    axios.get(`${baseUrl}/report/getSystemReports?time=2022-6-13 18:30:00`)
      .then(res => {
      console.log(res.data.user);
      if(res.data != undefined){
          console.log(res.data);
          setSystemReports(res.data);
      }
      }).catch(err => {
        console.log(err.response);
      })
    axios.get(`${baseUrl}/report/getUserReports?time=2022-6-13 18:30:00&userId=${loggedUserId}`)
        .then(res => {
        console.log(res.data);
        if(res.data != undefined){
            setUserReports(res.data);
        }
        }).catch(err => {
        console.log(err.response);
        })
  },[]);
  return (
  <Box sx={{ flexGrow: 1 }}>
    <Grid container spacing={2}>
        <Grid item xs={8}>
          <h1>System Reports</h1>
        </Grid>
        <Grid item xs={3}>
          <Button style={{margin:"15px 20px 0 20px"}} variant="contained" type="submit" onClick={(e) => history.push('/home')}  size="large">Go Back</Button>
        </Grid>
        <Grid item xs={12} style={{padding:"0 20px 0 20px"}}>
          <h2 style={{marginBottom:"0px"}}>System Reports</h2>
          <p>Number of Events Created = {systemReport === undefined ? "" : systemReport.noOfEvents}</p>
          <p>Of which no of Paid Events = {systemReport === undefined ? "" : systemReport.noOfPaidEvents}</p>
          <p>Number of Events Cancelled = {systemReport === undefined ? "" : systemReport.noOfCancelledEvents}</p>
          <p>Number of Total Requests Obtained For Cancelled Events = {systemReport === undefined ? "" : systemReport.totalRequestsObtainedForCancelledEvents}</p>
          <p>Total Number of participants Request / Minimum Required Participants = {systemReport === undefined ? "" :  systemReport.totalNoOfMinimumParticipantsForCancelledEvents}</p>
          <p>Number of Events Finished = {systemReport === undefined ? "" :  systemReport.noOfFinishedEvents} </p>
          <p>Average no of Participants of these events = {systemReport === undefined ? "" :  systemReport.totalParticipantsInFinishedEvents} </p>
        </Grid>
        { accountType === "P" ? <>
        <Grid item xs={12} style={{padding:"0 20px 0 20px"}}>
          <h2 style={{marginBottom:"0px"}}>User Level Reports - Participants</h2>
          <p>Number of SignedUp Events = {userReports === undefined ? "" : userReports.noOfSignedUpEventsByUser}</p>
          <p>Number of Approvals = {userReports === undefined ? "" : userReports.noOfApprovalsForUser}</p>
          <p>Number of Rejects = {userReports === undefined ? "" : userReports.noOfRejectsForUser}</p>
          <p>Number of Finished Events = {userReports === undefined ? "" : userReports.finishedEventsForUser}</p>
        </Grid>
        </> :null }
        <Grid item xs={12} style={{padding:"0 20px 0 20px"}}>
          <h2 style={{marginBottom:"0px"}}>User Level Reports - Organizer</h2>
          <p>Number of Events Created = {userReports === undefined ? "" : userReports.totalEventsCreated}</p>
          <p>Total of Paid Events = {userReports === undefined ? "" : userReports.totalPaidEvents}</p>
          <p>Number of Events Cancelled = {userReports === undefined ? "" : userReports.totalCancelledEvents}</p>
          <p>Total Events Finished = {userReports === undefined ? "" : userReports.totalFinishedEvents}</p>
          <p>Total Participants from Events Finished = {userReports === undefined ? "" : userReports.totalParticipantsInFinishedEvents}</p>
          <p>Total Revenue from Events Finished = {userReports === undefined ? "" : userReports.totalRevenueFromFinishedEvents}</p>
          <p>Total Number of participants Request [Cancelled]= {userReports === undefined ? "" : userReports.totalRequestForCancelledEvents}</p>
          <p>Total Number of Minimum Required Participants [Cancelled] = {userReports === undefined ? "" : userReports.totalMinimumParticipationForCancelledEvents}</p>
        </Grid>
      </Grid>
  </Box>
  );
}
export { SystemReports };