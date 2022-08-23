// import './App.css';
import { Router, Switch, Route, Redirect } from "react-router-dom";
import { LoginAccount } from "./Components/Auth/LoginUser";
import { RegisterAccount } from "./Components/Auth/RegisterUser";
import { Home } from "./Components/Pages/Home";
import { CreateEventForm } from "./Components/Pages/CreateEventForm";
import { EventRequest } from "./Components/Pages/EventRequest";
import { UserEvents } from "./Components/Pages/UserEvents";
import { EventPage } from "./Components/Pages/EventPage";
import history from "./utils/history";
import { ParticipantForum } from "./Components/Pages/ParticipantForum";
import { ApprovedParticipants } from "./Components/Pages/ApprovedParticipants";
import { UserReviews } from "./Components/Pages/UserReviews";
import { Oauth } from "./Components/Auth/Oauth";
import ImageUpload from "./Components/Auth/ImageUpload";
import { SystemReports } from "./Components/Pages/SystemReports";

function App() {
  return (
    <Router history={history}>
      <Switch>
        {/* <Route path="/create" component={CreateAccount} /> */}
        <Route exact path="/register" component={Oauth} />
        <Route exact path="/login" component={LoginAccount} />
        <Route exact path="/register/next" component={RegisterAccount} />
        <Route exact path="/home" component={Home} />
        <Route exact path="/create/event" component={CreateEventForm} />
        <Route exact path="/event/requests/:eventId" component={EventRequest} />
        <Route exact path="/event/myEvents" component={UserEvents} />
        <Route path="/event/id/:eventId" component={EventPage} />
        <Route path="/event/forum/:eventId" component={ParticipantForum} />
        <Route path="/event/approved/:eventId" component={ApprovedParticipants} />
        <Route path="/participant/reviews/:participantId" component={UserReviews} />
        <Route path="/Imageupload" component={ImageUpload} />
        <Route path="/reports" component={SystemReports} />
        {/* <PrivateRoute path="/jobseeker" component={JobSeekerHome} />
        <Route path="/employer" component={EmployerRoutes} />
        <Route path="/admin" component={Admin} /> */}
        <Redirect exact from="/" to="/login" />
      </Switch>
    </Router>
  );
}
export default App;
