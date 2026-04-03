import React from "react";
import { Link, useLocation } from "react-router-dom";
import "./Sidebar.css";

function Sidebar(){

const location = useLocation();

const menu = [
  { name: "Dashboard", path: "/" },
  { name: "Create Resume", path: "/create" },
  { name: "My Resumes", path: "/resumes" },
  { name: "ATS Analyzer", path: "/ats" },
  { name: "Job Match", path: "/match" }
];

return(

<div className="sidebar">

<h2 className="logo">🚀 AI Resume</h2>

<ul>

{menu.map((item,index)=>(

<li key={index}
className={location.pathname === item.path ? "active" : ""}
>

<Link to={item.path}>{item.name}</Link>

</li>

))}

</ul>

</div>

)

}

export default Sidebar;