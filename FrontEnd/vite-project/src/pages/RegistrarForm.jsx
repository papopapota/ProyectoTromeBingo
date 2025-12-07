import React, { useState } from "react";

export default function RegistrarForm({ onSubmit }) {
    const [form, setForm] = useState({
        nombre: "",
        email: "",
        password: "",
        confirmPassword: "",
        telefono: "",
        terms: false,
    });

    const [errors, setErrors] = useState({});

    const validate = (values) => {
        const errs = {};
        if (!values.nombre.trim()) errs.nombre = "El nombre es obligatorio.";
        if (!values.email) errs.email = "El email es obligatorio.";
        else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(values.email))
            errs.email = "Email inválido.";
        if (!values.password) errs.password = "La contraseña es obligatoria.";
        else if (values.password.length < 6)
            errs.password = "La contraseña debe tener al menos 6 caracteres.";
        if (values.password !== values.confirmPassword)
            errs.confirmPassword = "Las contraseñas no coinciden.";
        if (values.telefono && !/^\+?[\d\s\-]{7,15}$/.test(values.telefono))
            errs.telefono = "Teléfono inválido.";
        if (!values.terms) errs.terms = "Debes aceptar los términos.";
        return errs;
    };

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        setForm((prev) => ({
            ...prev,
            [name]: type === "checkbox" ? checked : value,
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const validation = validate(form);
        setErrors(validation);
        if (Object.keys(validation).length === 0) {
            // Acción por defecto: llamar al callback si lo hay, o hacer console.log
            if (typeof onSubmit === "function") onSubmit(form);
            else console.log("Datos enviados:", form);
            // Opcional: resetear formulario
            setForm({
                nombre: "",
                email: "",
                password: "",
                confirmPassword: "",
                telefono: "",
                terms: false,
            });
        }
    };

    return (
        <form onSubmit={handleSubmit} style={{ maxWidth: 480, margin: "0 auto" }}>
            <h2>Registrar</h2>

            <label>
                Nombre
                <input
                    name="nombre"
                    value={form.nombre}
                    onChange={handleChange}
                    placeholder="Tu nombre"
                    autoComplete="name"
                />
                {errors.nombre && <div style={{ color: "red" }}>{errors.nombre}</div>}
            </label>

            <label>
                Email
                <input
                    name="email"
                    type="email"
                    value={form.email}
                    onChange={handleChange}
                    placeholder="tu@ejemplo.com"
                    autoComplete="email"
                />
                {errors.email && <div style={{ color: "red" }}>{errors.email}</div>}
            </label>

            <label>
                Contraseña
                <input
                    name="password"
                    type="password"
                    value={form.password}
                    onChange={handleChange}
                    placeholder="********"
                    autoComplete="new-password"
                />
                {errors.password && (
                    <div style={{ color: "red" }}>{errors.password}</div>
                )}
            </label>

            <label>
                Confirmar contraseña
                <input
                    name="confirmPassword"
                    type="password"
                    value={form.confirmPassword}
                    onChange={handleChange}
                    placeholder="Repite la contraseña"
                    autoComplete="new-password"
                />
                {errors.confirmPassword && (
                    <div style={{ color: "red" }}>{errors.confirmPassword}</div>
                )}
            </label>

            <label>
                Teléfono (opcional)
                <input
                    name="telefono"
                    value={form.telefono}
                    onChange={handleChange}
                    placeholder="+51 9xxxxxxx"
                    autoComplete="tel"
                />
                {errors.telefono && (
                    <div style={{ color: "red" }}>{errors.telefono}</div>
                )}
            </label>

            <label style={{ display: "flex", alignItems: "center", gap: 8 }}>
                <input
                    name="terms"
                    type="checkbox"
                    checked={form.terms}
                    onChange={handleChange}
                />
                Acepto los términos y condiciones
            </label>
            {errors.terms && <div style={{ color: "red" }}>{errors.terms}</div>}

            <div style={{ marginTop: 12, display: "flex", gap: 8 }}>
                <button type="submit">Registrar</button>
                <button
                    type="button"
                    onClick={() =>
                        setForm({
                            nombre: "",
                            email: "",
                            password: "",
                            confirmPassword: "",
                            telefono: "",
                            terms: false,
                        })
                    }
                >
                    Limpiar
                </button>
            </div>
        </form>
    );
}