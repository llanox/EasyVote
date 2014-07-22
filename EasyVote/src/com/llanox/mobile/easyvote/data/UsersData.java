package com.llanox.mobile.easyvote.data;

import com.llanox.mobile.easyvote.model.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * 
 * @author Julian G�mez
 * Revisado y modificado por Juan Gabriel Gutierrez
 *
 */
public class UsersData extends DataAccess{
	private static final String TAG = UsersData.class.getSimpleName();
	
	private  Context mCtx;
	
	public static final String TABLE = "users";
	public static final String C_ID = "id_persona";
	public static final String C_USERNAME = "username";
	public static final String C_WEIGHT = "nombre";
	public static final String C_PASSWORD = "clave";
	private static final String C_ROLE = "role";
	
	
	private EasyVoteDBHelper mDbHelper;
	private SQLiteDatabase mDb;
	
	public UsersData(Context ctx) { 
		mDbHelper = EasyVoteDBHelper.getInstance(ctx);
	}
	
	public void open() throws SQLException {		
		mDb = mDbHelper.getWritableDatabase();		
	}
	
	public void close() { 
		mDbHelper.close();
	}

	public void insert( User user)  { 
		
		ContentValues values = new ContentValues();		
		values.put(C_USERNAME, user.getUsername());
		values.put(C_PASSWORD, user.getPassword());
		values.put(C_WEIGHT, user.getWeight());
		values.put(C_ID, user.getId());
		values.put(C_ROLE, user.getId());		
		Long id = mDb.insert(TABLE, null, values);
		user.setId(id);
		
		

	}
//	
//
//
//	private void validateInsert(long insert, String table) throws AbakoException {
//		
//		if(insert==-1){
//			throw new AbakoException("No fue posible insertar el registro en la tabla :"+table);
//		}
//		
//	}
//
//
//	
//	public void deleteAll() { 
//		
//		mDb.delete(TABLE, null,  null);
//	}	
//	
//	public Usuario getUsuario(String username, String idAgencia) { 
//		String[] columns={C_ID, C_USERNAME, C_CLAVE, C_ID_ROL, C_ID_AGENCIA, C_AGENCIAS_AUTORIZADAS};
//		String[] parameters={username, idAgencia};
//		String select = C_USERNAME + "=? AND "+C_ID_AGENCIA+"=?";
//		Usuario usuario = null;
//	
//		usuario = queryUsuario(columns,select, parameters);
//				
//	    return usuario;
//		
//	}
//
//	private Usuario queryUsuario(String[] columns,String select, String[] parameters) {
//		Usuario usuario= null;		
//		Cursor cursor=mDb.query(TABLE, columns, select , parameters, null, null, null);		
//			
//			if(cursor.moveToNext()){
//				usuario=new Usuario();
//				Agencia agencia=new Agencia();
//				usuario.setIdPersona(cursor.getString(cursor.getColumnIndex(C_ID)));
//				usuario.setUsername(cursor.getString(cursor.getColumnIndex(C_USERNAME)));
//				usuario.setClave(cursor.getString(cursor.getColumnIndex(C_CLAVE)));
//				usuario.setIdrol(cursor.getString(cursor.getColumnIndex(C_ID_ROL)));
//				
//				agencia.setId(cursor.getString(cursor.getColumnIndex(C_ID_AGENCIA)));
//				usuario.setAgencia(agencia);
//				usuario.setAgenciasAutorizadas(MappingUtil.mapStringToArray(cursor.getString(cursor.getColumnIndex(C_AGENCIAS_AUTORIZADAS)),Usuario.SEPARATOR_AGENCIAS));
//				
//			}
//			
//			cursor.close();
//			
//			return usuario;
//	}
//	
//
//	
//	/**
//	 * @author Juan Gabriel Gutierrez
//	 * @return Usuario - el único usuario que debe haber en la base de datos.
//	 */
//	public Usuario getUsuario() { 
//		String[] columns = {C_ID, C_USERNAME, C_CLAVE, C_ID_AGENCIA,C_NOMBRE,C_ID_ROL,C_AGENCIAS_AUTORIZADAS};
//		Usuario usuario = null;
//		usuario = queryUsuario(columns,null,null);		
//		
//		return usuario;
//	}
//	
//
//
//	public Boolean isUserTableEmpty() { 
//		String[] columns = {C_USERNAME};
//		
//		try {
//			Cursor cursor=mDb.query(TABLE, columns, null, null, null, null, null);
//			try {
//				return !cursor.moveToNext();
//			} finally {
//				cursor.close();
//			}
//		} finally {
//		}
//	}
//	
//	/**
//	 * Valida el login y el password ingresados con el usuario almacenado
//	 * en base de datos.
//	 * @return usuario, si este es valido
//	 * */	
//	public Usuario validarUsuario(Usuario userTmp) {
//		
//		Usuario user = this.getUsuario();
//		
//		if(user!=null && 
//				userTmp.getUsername().equalsIgnoreCase(user.getUsername()) &&
//				userTmp.getClave().equalsIgnoreCase(user.getClave()) &&
//				isAgenciaAutorizada(userTmp.getAgencia().getId(),user.getAgenciasAutorizadas())					
//		    )
//		  {
//			return user;
//		}
//		 
//		
//		return null;
//	
//		
//	}
//
//	private boolean isAgenciaAutorizada(String id, String[] agenciasAutorizadas) {
//		
//		for(String agencia: agenciasAutorizadas){
//			if(agencia.equalsIgnoreCase(id)){
//				return true;
//			}
//		}
//		
//		return false;
//	}

	@Override
	public String getRestPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getResourceName() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
