import * as React from 'react';
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import { CreateEvent } from '../Pages/CreateEvent';
import { UserEvents } from '../Pages/UserEvents';
import history from "../../utils/history";
import Fab from '@mui/material/Fab';
import NavigationIcon from '@mui/icons-material/Navigation';
import Modal from '@mui/material/Modal';
import { TextField } from '@mui/material';
import moment from 'moment';
import axios from 'axios';
import { baseUrl } from '../../utils/urls';
import { SystemReports } from '../Pages/SystemReports';

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

function TabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box sx={{ p: 3 }}>
          <Typography>{children}</Typography>
        </Box>
      )}
    </div>
  );
}

TabPanel.propTypes = {
  children: PropTypes.node,
  index: PropTypes.number.isRequired,
  value: PropTypes.number.isRequired,
};

function a11yProps(index) { 
  return {
    id: `simple-tab-${index}`,
    'aria-controls': `simple-tabpanel-${index}`,
  };
}

export default function NavBar() {
  if(localStorage.getItem("userId")===null || localStorage.getItem("userId")===undefined || localStorage.getItem("userId").length===0){
    history.push("/login");
  }
  let loggedUser = localStorage.getItem("userId");
  loggedUser = JSON.parse(loggedUser);
  const loggedUserId = loggedUser.id;
  const accountType = loggedUser.accountType;
  const [value, setValue] = React.useState(0);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };
  const logout = () =>{
    console.log("logout");
    localStorage.removeItem("userId");
    history.push("/");
  }
  if(localStorage.getItem("systemTime") == null || localStorage.getItem("systemTime") == undefined){
    localStorage.setItem("systemTime", moment(moment()._d).format('yyyy-MM-DD kk:mm:ss'));
  }
  
  const [open, setOpen] = React.useState(false);
  const [systemTime, setSystemTime] = React.useState(localStorage.getItem("systemTime"));
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  const changeTime = () => {

    axios.put(`${baseUrl}/time/changeCurrentTime?time=${moment(systemTime).format('yyyy-MM-DD kk:mm:ss')}`)
    .then(res => {
    console.log("Time Changed");
    setOpen(false);
    localStorage.setItem("systemTime", systemTime);
    // <Redirect to={"/home"}/>
    window.location.reload(true);
    console.log(res);
    }).catch(err => {
    console.log(err.response);
    })
  }

  return (
    <Box sx={{ width: '100%' }}>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
        <h1>Enter System Time</h1>
        <TextField
            id="outlined-uncontrolled"
            label="System Time Change"
            type="datetime-local"
            style={{width :"80%"}}
            onChange={(e) => setSystemTime(e.target.value)}
            value={systemTime}
            required
          /><br></br>
          <br></br>
          <Button variant="contained" onClick={changeTime} size="large">Change</Button>
        </Box>
      </Modal>
      <Box sx={{ borderBottom: 1, borderColor: 'divider'}}>
        <Tabs value={value} onChange={handleChange} aria-label="basic tabs example" >
          <Tab label="Home" {...a11yProps(0)} />
          {accountType==="P"?<Tab label="User Events" {...a11yProps(1)} /> : null}
          {/* <Tab label="System Reports" {...a11yProps(2)} /> */}
          <Fab variant="extended" size="small" color="primary" onClick={handleOpen} style={{margin:"10px 10px 10px 10px"}} aria-label="add">
            <NavigationIcon sx={{ mr: 1 }} />
            Adjust System Time
          </Fab> 
          <p>Current Time { systemTime }</p>
          <Button variant="contained" onClick={()=>{history.push("/reports")}} style={{margin:"10px 10px 10px 10px"}}>Check Reports</Button>
          <Button variant="contained" onClick={logout} style={{margin:"10px 10px 10px 10px"}}>Logout</Button>
        </Tabs>
      </Box>
      <TabPanel value={value} index={0}>
          <CreateEvent/>
      </TabPanel>
      {/* <TabPanel value={value} index={2}>
        System Reports
        <SystemReports/>
      </TabPanel> */}
      <TabPanel value={value} index={1}>
        <UserEvents/>
      </TabPanel>

    </Box>
  );
}