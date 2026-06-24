import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import "./App.css";

import Sidebar from "./components/Sidebar";

import Dashboard from "./pages/Dashboard";
import CreateResume from "./pages/CreateResume";
import MyResumes from "./pages/MyResumes";
import AtsAnalyzer from "./pages/AtsAnalyzer";
import JobMatch from "./pages/JobMatch";

function App(){
return(
<Router>
<div className="app">
<Sidebar/>
<div className="main">
<Routes>
<Route path="/" element={<Dashboard/>}/>
<Route path="/create" element={<CreateResume/>}/>
<Route path="/resumes" element={<MyResumes/>}/>
<Route path="/ats" element={<AtsAnalyzer/>}/>
<Route path="/match" element={<JobMatch/>}/>
</Routes>
</div>
</div>
</Router>
)
}
export default App;