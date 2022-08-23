import * as React from 'react';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Typography from '@mui/material/Typography';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import { styled, alpha } from '@mui/material/styles';
import Button from '@mui/material/Button';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import Divider from '@mui/material/Divider';
import ArchiveIcon from '@mui/icons-material/Archive';
import FileCopyIcon from '@mui/icons-material/FileCopy';
import axios from 'axios';
import history from '../../utils/history';
import moment from 'moment';
import Box from '@mui/material/Box';
import Modal from '@mui/material/Modal';
import Chip from '@mui/material/Chip';
import { baseUrl } from '../../utils/urls';
import { PropaneSharp } from '@mui/icons-material';

const StyledMenu = styled((props) => (
    <Menu
      elevation={0}
      anchorOrigin={{
        vertical: 'bottom',
        horizontal: 'right',
      }}
      transformOrigin={{
        vertical: 'top',
        horizontal: 'right',
      }}
      {...props}
    />
  ))(({ theme }) => ({
    '& .MuiPaper-root': {
      borderRadius: 6,
      marginTop: theme.spacing(1),
      minWidth: 180,
      color:
        theme.palette.mode === 'light' ? 'rgb(55, 65, 81)' : theme.palette.grey[300],
      boxShadow:
        'rgb(255, 255, 255) 0px 0px 0px 0px, rgba(0, 0, 0, 0.05) 0px 0px 0px 1px, rgba(0, 0, 0, 0.1) 0px 10px 15px -3px, rgba(0, 0, 0, 0.05) 0px 4px 6px -2px',
      '& .MuiMenu-list': {
        padding: '4px 0',
      },
      '& .MuiMenuItem-root': {
        '& .MuiSvgIcon-root': {
          fontSize: 18,
          color: theme.palette.text.secondary,
          marginRight: theme.spacing(1.5),
        },
        '&:active': {
          backgroundColor: alpha(
            theme.palette.primary.main,
            theme.palette.action.selectedOpacity,
          ),
        },
      },
    },
  }));

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
  
function EventCard(props) {
  const [anchorEl, setAnchorEl] = React.useState(null);
  let loggedUser = localStorage.getItem("userId");
  if(loggedUser == undefined || loggedUser === null){
    history.push("/login");
  }
  loggedUser = JSON.parse(loggedUser);
  const loggedUserId = loggedUser.id;
  const accountType = loggedUser.accountType;
  let eventParticipated  = props.data.participants.map((lep)=>{
    return lep.user.id;
  });
  console.log(Object.values(eventParticipated).includes(loggedUserId));
  console.log("account type", props.accountType);
  let startDate = moment(props.data.start_date).format('yyyy-MM-DD kk:mm:ss');
  let endDate = moment(props.data.end_date).format('yyyy-MM-DD kk:mm:ss');
  let deadlineDate = moment(props.data.deadline).format('yyyy-MM-DD kk:mm:ss');
  const [openModal, setOpenModal] = React.useState(false);
  const handleOpenModal = () => setOpenModal(true);
  const handleCloseModal = () => {
    setOpenModal(false);
    axios.post(`${baseUrl}/event/eventSignUp?userId=${loggedUserId}&eventId=${props.data.id}&approval=0`).then(res => {
        console.log(res.data);
        window.location.reload(false);
        }).catch(err => {
        console.log(err.response);
      });
  }
  const open = Boolean(anchorEl);
  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  const detailsClick = () => {
    console.log("Id", props.data.id);
    history.push(`/event/id/${props.data.id}`);
    setAnchorEl(null);
  };

  const approveClick = () => {
    console.log("Id", props.data.id);
    history.push(`/event/requests/${props.data.id}`);
    setAnchorEl(null);
  };
  const participantsClick = () => {
    console.log("Id", props.data.id);
    history.push(`/event/approved/${props.data.id}`);
    setAnchorEl(null);
  };
  const deleteClick = () => {
    console.log("Id", props.data.id);
    axios.put(`${baseUrl}/event/cancelEvent?eventID=${props.data.id}`).then(res => {
      console.log(res.data);
      }).catch(err => {
      console.log(err.response);
      });
    setAnchorEl(null);
  };
  const rsvpClick = () => {
    console.log("Id", props.data.id);
    if(props.data.fees>0){
      console.log("please pay");
      setOpenModal(true);
    }else{
      axios.post(`${baseUrl}/event/eventSignUp?userId=${loggedUserId}&eventId=${props.data.id}&approval=0`).then(res => {
        console.log(res.data);
        }).catch(err => {
        console.log(err.response);
        });
    }
  };
  const forumClick = () => {
    history.push(`/event/forum/${props.data.id}`);
  };


  return (
    <Card >
      <Modal
        open={openModal}
        onClose={handleCloseModal}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <Typography id="modal-modal-title" variant="h6" component="h2">
            Fee Payment {props.data.fees} 
          </Typography>
          <Typography id="modal-modal-description" sx={{ mt: 2 }}>
            Event : {props.data.title}<br></br>
            Fees : {props.data.fees}
          </Typography>
          <Button variant="contained" onClick={handleCloseModal}  size="large" style={{marginBottom:"20px 0px 40px 0px"}}>Authorize Payment</Button>
        </Box>
      </Modal>
      { accountType === "O" || props.data.userId === loggedUserId ? 
      <>
      <CardHeader  
        action={
            <div>
                <Button
                    id="demo-customized-button"
                    aria-controls={open ? 'demo-customized-menu' : undefined}
                    aria-haspopup="true"
                    aria-expanded={open ? 'true' : undefined}
                    disableElevation
                    onClick={handleClick}
                >
                    <MoreVertIcon />
                </Button>
                <StyledMenu
                    id="demo-customized-menu"
                    MenuListProps={{
                    'aria-labelledby': 'demo-customized-button',
                    }}
                    anchorEl={anchorEl}
                    open={open}
                    onClose={handleClose}
                >
                    {/* <MenuItem onClick={detailsClick} disableRipple>
                    <EditIcon />
                    View Details
                    </MenuItem> */}
                    <MenuItem onClick={approveClick} disableRipple>
                    <ArchiveIcon />
                    Approve Request
                    </MenuItem>
                    <Divider sx={{ my: 0.5 }} />
                    <MenuItem onClick={participantsClick} disableRipple>
                    <FileCopyIcon />
                    Event Participants
                    </MenuItem>
                    
                </StyledMenu>
                </div>
        }
        title={props.data.title}
        subheader={props.data.user.screenName}
      />
      </> : 
      <CardHeader
        // {props.data.id === loggedUserId ? "" : ""}
        title={props.data.title}
        subheader={props.data.user.screenName}
      />}
      <CardContent style={{paddingTop:"0px"}}>
        <Typography variant="body2">
        { props.data.status === 1 ? <><Chip label="Cancelled" style={{background:"red"}}/> - { props.data.forumCloseReason !== null ?  props.data.forumCloseReason : null }<br></br></> : null }
        { props.data.status === 0 ? <><Chip label="Open for registration"/><br></br></> : null }
        { props.data.status === 2 ? <><Chip label="Active"/><br></br></> : null }
        { props.data.status === 3 ? <><Chip label="Past"/><br></br></> : null }
        { props.data.participantsCount===props.data.maxParticipants ? <><Chip label="Full"/><br></br></> : null }
          Location: {props.data.address.city}, {props.data.address.state}<br></br>
          Fee : {props.data.fees}<br></br>
          Starts at: {startDate}<br></br>
          End at: {endDate}<br></br>
          Deadline: {deadlineDate}<br></br>
          Registered Participants : {props.data.participantsCount}<br></br>
          Maximum Participants: {props.data.maxParticipants}<br></br>
          policy: {props.data.policy}<br></br>
        </Typography>
      </CardContent>
      <CardActions style={{float:"right"}}>
        <Button variant="contained" onClick={detailsClick} size="large">View Details</Button>
        { accountType === "O"?  <><Button variant="contained" onClick={forumClick} size="large">Forum</Button></> : <><Button variant="contained" disabled={!Object.values(eventParticipated).includes(loggedUserId) || accountType === "O"} onClick={forumClick} size="large">Forum</Button></>}
        { accountType === "O" || props.data.userId === loggedUserId ? null : <>
          <Button variant="contained" disabled={props.data.participantsCount===props.data.maxParticipants || props.data.status !== 0 || Object.values(eventParticipated).includes(loggedUserId)} onClick={rsvpClick} size="large">RSVP</Button>
        </> }
      </CardActions>
    </Card>
  );
}

export {EventCard};