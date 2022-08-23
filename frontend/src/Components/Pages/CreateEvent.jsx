import * as React from 'react';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import Button from '@mui/material/Button';
import Modal from '@mui/material/Modal';
import { EventCard } from '../Common/EventCard';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import TextField from '@mui/material/TextField';
import axios from 'axios';
import moment from 'moment';
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

const styleModal = {
    margin : "10px",
    width : "80%",

  };

function CreateEvent() {
  let loggedUser = localStorage.getItem("userId");
  if(loggedUser == undefined || loggedUser === null){
    history.push("/login");
  }
  loggedUser = JSON.parse(loggedUser);
  const loggedUserId = loggedUser.id;
  const accountType = loggedUser.accountType;
  const [eventMultipleData, setEventMultipleData] = React.useState();
  const [userCreatedData, setUserCreatedData] = React.useState();
  const [filterData, setFilterData] = React.useState({
    location : "",
    status : "all",
    startTime : moment(moment()._d).format('yyyy-MM-DD kk:mm:ss'),
    endTime : moment(moment()._d).add(500000,'hours').format('yyyy-MM-DD kk:mm:ss'),
    Keyword : "",
    organizer : ""
  });
  // let status = "active";
  React.useEffect(() => {
    axios.get(`${baseUrl}/event/searchEvent?location=&status=${filterData.status}&startTime=${moment(moment()._d).format('yyyy-MM-DD kk:mm:ss')}&endTime=&keyword=&organizer=`)
      .then(res => {
      console.log(res.data);
      if(res.data != undefined){
        setEventMultipleData(res.data);
      }
      }).catch(err => {
      console.log(err.response);
      })
    axios.get(`${baseUrl}/event/getEventsCreatedByUser?userId=${loggedUserId}`)
      .then(res => {
      console.log(res.data);
      if(res.data != undefined){
        setUserCreatedData(res.data);
      }
      }).catch(err => {
      console.log(err.response);
    })
  },[]);
  const [searchWord, setSearchWord] = React.useState();
  // const searchEvent = () =>{
  //   axios.get(`${baseUrl}/event/searchEvent?location=&status='active'&startTime=${moment(moment()._d).format('yyyy-MM-DD kk:mm:ss')}&endTime=&keyword=${searchWord}&organizer=`)
  //   .then(res => {
  //   console.log(res.data);
  //   if(res.data != undefined){
  //     setEventMultipleData(res.data);
  //   }
  //   }).catch(err => {
  //   console.log(err.response);
  //   })
  // }

  const filterDataClick = () =>{
    setOpen(false);
    axios.get(`${baseUrl}/event/searchEvent?location=${filterData.location}&status=${filterData.status}&startTime=${moment(filterData.startTime).format('yyyy-MM-DD kk:mm:ss')}&endTime=${moment(filterData.endTime).format('yyyy-MM-DD kk:mm:ss')}&keyword=${filterData.Keyword}&organizer=${filterData.organizer}`)
    .then(res => {
    console.log(res.data);
    if(res.data !== undefined){
      setEventMultipleData(res.data);
    }
    }).catch(err => {
    console.log(err.response);
    })
  }
  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  return(
    <Box sx={{ flexGrow: 1 }}>
      <Grid container spacing={2}>
        <Grid item xs={8}>
            <h1 style={{margin:"0px"}}>Events</h1>
        </Grid>
        <Grid item xs={4}>
          <Button 
          variant="contained"  
          onClick={() => { history.push("/create/event")}}
          size="large"
          >
              + Create Event
          </Button>
        </Grid>
        { accountType === "O" ? null : <>
        {/* <Grid item xs={6}>
            <TextField
                id="Controlled"
                label="Search"
                defaultValue=""
                value={searchWord}
                onChange={(e)=>setSearchWord(e.target.value)}
                style={{marginRight : "25px", width:"60%"}}
            />
            <Button variant="contained" onClick={searchEvent} size="large" style={{margin:"5px"}}>Search</Button>
        </Grid> */}
        <Grid item xs={12}>
            <Button variant="contained"  size="large" onClick={handleOpen} style={{margin:"5px"}}>Filter +</Button>
            <Modal
            open={open}
            onClose={handleClose}
            aria-labelledby="parent-modal-title"
            aria-describedby="parent-modal-description"
            >
                <Box sx={{ ...style, width: 400 , textAlign:"center"}}>
                <h1>Filter Events</h1>
                    <TextField
                        id="outlined-uncontrolled"
                        label="Location"
                        style={{...styleModal}}
                        onChange={(e) => setFilterData({...filterData, location: e.target.value})}
                        value={filterData.location}
                    />
                    <FormControl fullWidth style={{...styleModal}}>
                        <InputLabel id="demo-simple-select-label">Status</InputLabel>
                        <Select
                        labelId="demo-simple-select-label"
                        id="demo-simple-select"
                        label="Status"
                        onChange={(e) => setFilterData({...filterData, status: e.target.value})}
                        value={filterData.status}
                        required
                        >
                        <MenuItem value={"openForRegistrations"}>Open for Registration</MenuItem>
                        <MenuItem value={"active"} >Active</MenuItem>
                        <MenuItem value={"all"}>All</MenuItem>
                        </Select>
                    </FormControl>

                    <TextField
                        id="outlined-uncontrolled"
                        label="Organizer"
                        style={{...styleModal}}
                        onChange={(e) => setFilterData({...filterData, organizer: e.target.value})}
                        value={filterData.organizer}
                    />
                    <TextField
                        id="Controlled"
                        label="Start Date/Time"
                        type="datetime-local"
                        style={{...styleModal}}
                        onChange={(e) => setFilterData({...filterData, startTime: e.target.value})}
                        value={filterData.startTime}
                    />
                    <TextField
                        id="Controlled"
                        label="Event Date"
                        type="datetime-local"
                        style={{...styleModal}}
                        onChange={(e) => setFilterData({...filterData, endTime: e.target.value})}
                        value={filterData.endTime}
                    />
                    <TextField
                        id="outlined-uncontrolled"
                        label="Keyword"
                        style={{...styleModal}}
                        onChange={(e) => setFilterData({...filterData, Keyword: e.target.value})}
                        value={filterData.Keyword}
                    />
                    <Button variant="contained" onClick={filterDataClick}  size="large" style={{marginBottom:"20px 0px 40px 0px"}}>Filter Results</Button>
                </Box>
            </Modal>
        </Grid>
          {(eventMultipleData != undefined)? eventMultipleData.map(ed => (
            <Grid item xs={6} lg={4} key={ed.id}>
              <EventCard data = {ed} accountType={accountType}/>
            </Grid>
          )): " " }
        </> }
          {(userCreatedData != undefined)? userCreatedData.map(cd => (
          <Grid item xs={6} lg={4}>
              <EventCard data={cd} accountType={accountType}/>
          </Grid>
          )): " " }          
      </Grid>
    </Box>
  );
}

export { CreateEvent };
