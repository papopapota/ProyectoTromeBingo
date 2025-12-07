import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter } from 'react-router-dom'
import 'bootstrap/dist/css/bootstrap.min.css'
import './index.css'
import App from './App.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <div className='m-4' style={{ width: '100%', maxWidth: '100%', padding: 0, margin: 0 }}>
      <BrowserRouter>
        <App />
      </BrowserRouter>
    </div>
  </StrictMode>,
)
