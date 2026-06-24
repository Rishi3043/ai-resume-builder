import React from "react";
import "./PageStyles.css";

function Dashboard(){
return(
<div className="page">
<h1>AI Resume Dashboard</h1>

<div className="grid">

<div className="card">
<h2>Create Resume</h2>
<p>Build resume using AI</p>
</div>

<div className="card">
<h2>My Resumes</h2>
<p>Manage resumes</p>
</div>

<div className="card">
<h2>ATS Analyzer</h2>
<p>Check ATS score</p>
</div>

<div className="card">
<h2>Job Match</h2>
<p>Match with jobs</p>
</div>

</div>
</div>
)
}

export default Dashboard;