export async function fetchCabs() {
  const res = await fetch('/api/cabs')
  if (!res.ok) throw new Error('Network response was not ok')
  return res.json()
}

export async function fetchCab(id) {
  const res = await fetch(`/api/cab/${encodeURIComponent(id)}`)
  if (!res.ok) throw new Error('Network response was not ok')
  return res.json()
}

export async function compareCab(id) {
  const res = await fetch(`/api/compare/${encodeURIComponent(id)}`)
  if (!res.ok) throw new Error('Network response was not ok')
  return res.json()
}

export async function registerBingo(data) {
  const res = await fetch('/api/register', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(data)
  })
  if (!res.ok) throw new Error('Network response was not ok')
  return res.json()
} 
