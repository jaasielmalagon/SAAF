/*DATOS DE EMPLEADOS*/
SELECT personas.idPersona, personas.nombre, personas.apaterno, personas.amaterno, personas.curp, personas.telefono, personas.celular, personas.sexo,
tipo_cargo.cargo, tipo_estudios.estudios, tipo_usuarios.tipo as depto, personas_empleados.llamara as contacto_emergencia, personas_empleados.dias
FROM personas_empleados
LEFT JOIN personas ON personas_empleados.idPersona = personas.idPersona
RIGHT JOIN tipo_cargo ON personas_empleados.cargo = tipo_cargo.idCargo
RIGHT JOIN tipo_estudios ON personas_empleados.estudios = tipo_estudios.idTipoEstudios
RIGHT JOIN tipo_usuarios ON personas_empleados.departamento = tipo_usuarios.idTipoUsuario
WHERE personas_empleados.sucursal = 1
/*<-- fin DATOS DE EMPLEADOS*/