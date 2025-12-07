import React from 'react';

export default function Nav() {
  return (
    <>
      {/* Barra superior negra */}
      <nav className="navbar" style={{ backgroundColor: '#211f20' }}>
        <div className="container-fluid d-flex justify-content-center">
          <img
            src="/img/trome.jpg"
            alt="trome"
            style={{ height: '7em' }}
          />
        </div>
      </nav>

      {/* Franja naranja completa */}
      <div
        style={{
          width: '100%',
          height: '2.5em',
          backgroundColor: '#f66f1e'
        }}
      ></div>
    </>
  );
}
