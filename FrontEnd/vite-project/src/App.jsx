import React from 'react'
import { Routes, Route } from 'react-router-dom'
import Nav from './components/Nav'
import IndexPage from './pages/Index'
import DetallePage from './pages/Detalle'
import RevisionPage from './pages/Revision'
// import CrudNumeroPage from './pages/CrudNumero'
import RegistrarForm from './pages/registrarForm'
import './App.css'

function App() {
  return (
    <>
      <Nav />
      <div className="container-fluid px-4 py-3">
        <Routes>
          <Route path="/" element={<IndexPage />} />
          <Route path="/detalle/:id" element={<DetallePage />} />
          <Route path="/comparar/:id" element={<RevisionPage />} />
          {/* <Route path="/cargarCrudNumero" element={<CrudNumeroPage />} /> */}
          <Route path="/RegistrarForm" element={<RegistrarForm />} />

        </Routes>
      </div>
    </>
  )
}


export default App
