-- Renombrar la tabla perfil a perfiles
ALTER TABLE perfil RENAME TO perfiles;

-- Renombrar la tabla usuario a usuarios
ALTER TABLE usuario RENAME TO usuarios;

-- Renombrar la tabla usuario_perfil a usuarios_perfiles
ALTER TABLE usuario_perfil RENAME TO usuarios_perfiles;

-- Renombrar la tabla curso a cursos
ALTER TABLE curso RENAME TO cursos;

-- Renombrar la tabla topico a topicos
ALTER TABLE topico RENAME TO topicos;

-- Renombrar la tabla respuesta a respuestas
ALTER TABLE respuesta RENAME TO respuestas;