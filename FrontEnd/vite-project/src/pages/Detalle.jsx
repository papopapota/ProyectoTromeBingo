import React, { useEffect, useState } from 'react'
import { useParams, Link } from 'react-router-dom'
import { fetchCab } from '../services/api'

export default function DetallePage() {
  const { id } = useParams()
  const [cab, setCab] = useState(null)
  const [detalleList, setDetalleList] = useState([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    if (!id) return
    setLoading(true)
    fetchCab(id)
      .then((data) => {
        setCab(data)
        if (data && data.detalleList) setDetalleList(data.detalleList)
      })
      .catch((err) => console.error('Error fetching cab', err))
      .finally(() => setLoading(false))
  }, [id])

  if (loading) return <div>Cargando...</div>
  if (!cab) return <div>No se encontró el bingo {id}</div>
  console.log(detalleList);
  return (
    <div>
      <h2>Detalle Bingo: {cab.idBingo}</h2>
      <div className="mb-3">
        <Link to={`/comparar/${cab.idBingo}`} className="btn btn-info">
          Comparar numeros
        </Link>
        <Link to="/" className="btn btn-secondary ms-2">
          Volver
        </Link>
      </div>

      <table className="table table-bordered">
        <thead>
          <tr>
            <th>#</th>
            <th>B</th>
            <th>I</th>
            <th>N</th>
            <th>G</th>
            <th>O</th>
          </tr>
        </thead>
        <tbody>
          {detalleList && detalleList.length > 0 ? (
            detalleList.map((d, idx) => (
              <tr key={d.id || idx}>
                <td>{idx + 1}</td>
                <td>{d.b}</td>
                <td>{d.i}</td>
                <td>{d.n}</td>
                <td>{d.g}</td>
                <td>{d.o}</td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan={6}>No hay líneas</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  )
}
