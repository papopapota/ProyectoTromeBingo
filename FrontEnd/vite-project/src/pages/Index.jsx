import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { fetchCabs } from '../services/api'

export default function IndexPage() {
  const [lstcab, setLstcab] = useState([])
  const [mensaje, setMensaje] = useState(null)

  useEffect(() => {
    fetchCabs()
      .then((data) => setLstcab(data))
      .catch((err) => console.error('Error fetching cabs', err))
  }, [])

  return (
    <>
      <div className="d-flex gap-2 mb-4">
        <Link to="/RegistrarForm" className="btn btn-primary btn-lg">
          Registrar Bingo
        </Link>
        <Link to="/cargarCrudNumero" className="btn btn-primary btn-lg">
          Crud Numeros
        </Link>
        <Link to="/revisarTodosBingos" className="btn btn-primary btn-lg">
          Revisar Todos los Bingos
        </Link>
      </div>

      {mensaje && (
        <div className="alert alert-success" role="alert">
          <h4 className="alert-heading">Exito !!</h4>
          <div dangerouslySetInnerHTML={{ __html: mensaje }} />
        </div>
      )}

      <table className="table table-striped table-hover">
        <thead>
          <tr>
            <th scope="col">Id Bingo</th>
            <th scope="col">Estado</th>
            <th scope="col">#</th>
          </tr>
        </thead>
        <tbody>
          {lstcab && lstcab.map((c) => (
            <tr key={c.idBingo}>
              <th scope="row">
                <Link className="btn btn-outline-success" to={`/detalle/${c.idBingo}`}>
                  {c.idBingo}
                </Link>
              </th>
              <th scope="row">
                <span className="form-control p-2 pe-2">
                  {c.revision == null || c.revision === false ? 'Falta por revisar' : 'Revisado'}
                </span>
              </th>
              <th scope="row">
                <Link className="btn btn-outline-info" to={`/comparar/${c.idBingo}`}>
                  Comparar numeros
                </Link>
              </th>
            </tr>
          ))}
        </tbody>
      </table>
    </>
  )
}
