import { useEffect, useState } from "react";

export default function Adding() {

  const [outcome, setOutcome] = useState(0);

  const [first, setFirst] = useState(0);

  const [second, setSecond] = useState(0);

  const sum = (e) =>{
    e.preventDefault()
    fetch('http://localhost:8080/sum?first=' + first + '&second=' + second)
    .then(response=>response.text())
    .then(result=>setOutcome(result))
  }

  const ras = (e) =>{
    e.preventDefault()
    fetch('http://localhost:8080/ras?first=' + first + '&second=' + second)
    .then(response=>response.text())
    .then(result=>setOutcome(result))
  }
  const del = (e) =>{
    e.preventDefault()
    fetch('http://localhost:8080/del?first=' + first + '&second=' + second)
    .then(response=>response.text())
    .then(result=>setOutcome(result))
  }
  const pros = (e) =>{
    e.preventDefault()
    fetch('http://localhost:8080/pros?first=' + first + '&second=' + second)
    .then(response=>response.text())
    .then(result=>setOutcome(result))
  }

  return (
    <main className="flex-shrink-0">
      <div className="p-2">
      <form>
        <div className="form-group">
          <label>Первое число</label>
          <input type='number' value = {first} onChange={event => setFirst(event.target.value)} className="form-control"/>
        </div>
        <div className="form-group">
          <label >Второе число</label>
          <input type='number' value = {second} onChange={event => setSecond(event.target.value)} className="form-control"/>
        </div>
        <button onClick={sum} type="submit" className="btn btn-primary">Sum</button>
        <button onClick={ras} type="submit" className="btn btn-primary">-</button>
        <button onClick={del} type="submit" className="btn btn-primary">/</button>
        <button onClick={pros} type="submit" className="btn btn-primary">*</button>        
        {outcome}
      </form>
      </div>
    </main>
  );
}
