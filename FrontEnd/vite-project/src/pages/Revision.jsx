import React, { useEffect, useState } from 'react'
import { useParams, Link } from 'react-router-dom'
import { compareCab } from '../services/api'

export default function RevisionPage() {
  const { id } = useParams()
  const [result, setResult] = useState(null)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    if (!id) return
    setLoading(true)
    compareCab(id)
      .then((data) => setResult(data))
      .catch((err) => console.error('Error comparing cab', err))
      .finally(() => setLoading(false))
  }, [id])

  if (loading) return <div>Cargando revisión...</div>
  if (!result) return <div>No se encontró información para {id}</div>
  return (
    <div>
      <h2>Revisión Bingo: {result.cab?.idBingo}</h2>
      <div className="mb-3">
        <Link to={`/detalle/${result.cab?.idBingo}`} className="btn btn-secondary">
          Ver detalle
        </Link>
        <Link to="/" className="btn btn-link ms-2">
          Volver al inicio
        </Link>
      </div>

      {/* <div className="alert alert-info">Numeros buenos: {result.numeroBuenos}</div> */}

      <h4>Listado de líneas</h4>
      <table className="table">
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
          {result.detalleList && result.detalleList.length > 0 ? (
            
            result.detalleList.map((d, idx) => (
              <tr key={d.id || idx}>
                <td >{idx + 1}</td>
                <td className={` ${result.numeroBuenos.includes(d.b) ? 'table-success' : ''} px-4 py-2`}>{d.b}</td>
                <td className={` ${result.numeroBuenos.includes(d.i) ? 'table-success' : ''} px-4 py-2`}>{d.i}</td>
                <td className={` ${result.numeroBuenos.includes(d.n) ? 'table-success' : ''} px-4 py-2`}>{d.n}</td>
                <td className={` ${result.numeroBuenos.includes(d.g) ? 'table-success' : ''} px-4 py-2`}>{d.g}</td>
                <td className={` ${result.numeroBuenos.includes(d.o) ? 'table-success' : ''} px-4 py-2`}>{d.o}</td>
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
