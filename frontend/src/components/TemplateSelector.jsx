import { useEffect, useState } from "react";

function TemplateSelector() {
  const [templates, setTemplates] = useState([]);
  const [selected, setSelected] = useState(null);

  useEffect(() => {
    fetch("http://localhost:8080/api/templates")
      .then(res => res.json())
      .then(data => setTemplates(data))
      .catch(err => console.error(err));
  }, []);

  return (
    <div style={{ padding: "20px" }}>
      <h2>Select Resume Template</h2>

      <div style={{ display: "flex", gap: "20px" }}>
        {templates.map(template => (
          <div
            key={template.id}
            onClick={() => setSelected(template.id)}
            style={{
              border: selected === template.id ? "3px solid blue" : "1px solid gray",
              padding: "15px",
              cursor: "pointer",
              width: "200px"
            }}
          >
            <h4>{template.name}</h4>
            <p>{template.description}</p>
          </div>
        ))}
      </div>
    </div>
  );
}

export default TemplateSelector;