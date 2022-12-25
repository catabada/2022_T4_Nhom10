import React, { Fragment, useEffect, useState } from 'react';
import Navigation from 'components/layout/navigation';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-icons/font/bootstrap-icons.css';
import Header from 'components/layout/header';
import WeatherDetail from 'pages/weather-detail';
import { Route, Routes } from 'react-router-dom';

function App() {
  const [codeProvince, setCodeProvince] = useState("");
  const updateCode = (code: any) => {
    setCodeProvince(code);
  }
  
  return (
    <div>
      <Header codeProvince={codeProvince} />
      <Navigation />
      <Fragment>
        <Routes>
          <Route path="/:code" element={<WeatherDetail updateCode={updateCode} />} />
        </Routes>
      </Fragment>
    </div>
  );
}

export default App;
