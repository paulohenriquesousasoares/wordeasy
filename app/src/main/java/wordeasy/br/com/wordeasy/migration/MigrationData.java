package wordeasy.br.com.wordeasy.migration;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmSchema;
import wordeasy.br.com.wordeasy.dominio.Usuario;

public class MigrationData implements RealmMigration {

    public static final int VERSION = 0;

    @Override
    public void migrate(DynamicRealm realm, long loldVersion, long newVersion) {

        RealmSchema schema = realm.getSchema();

        if(newVersion ==  0) {createSchema(schema);}
//        if(newVersion == 1) {
//            schema.get("Palavra")
//                    .addField("CardPersonalizado",boolean.class)
//                    .addField("NaoEstudar",boolean.class);
//
//        }
//        if(newVersion ==  1) { schema.get("Palavra").removeField("teste");}
//
//        if(newVersion ==  2) {
//            schema.create("Configuracao")
//                    .addField("id", long.class, FieldAttribute.PRIMARY_KEY)
//                    .addField("itensPorSessaoRevisao", int.class)
//                    .addField("itensPorSessaoEstudo", int.class)
//                    .addField("hora", int.class)
//                    .addField("minuto", int.class)
//                    .addField("usuarioId", long.class);
//        }
//
//        if(newVersion ==  3) {schema.get("Configuracao").addField("ativo", boolean.class);}
//
//        if(newVersion ==  5){
//            schema.get("Palavra")
//                    .addPrimaryKey("PalavraId")
//                    .addField("qtdErros", int.class)
//                    .addField("qtdAcertos", int.class)
//                    .addField("qtdVezesEstudou", int.class);
//        }
    }

    public void createSchema(RealmSchema schema) {

        //Usuario
        schema.create("Usuario")
                .addField("id", long.class, FieldAttribute.PRIMARY_KEY)
                .addField("nome", String.class)
                .addField("email", String.class)
                .addField("senha", String.class);


        //Palavra
        schema.create("Palavra")
                .addField("id", long.class, FieldAttribute.PRIMARY_KEY)
                .addField("PalavraEmIngles", String.class)
                .addField("PalavraEmPortugues", String.class)
                .addField("IndicePalavra", String.class)
                .addField("Favorito", Boolean.class)
                .addRealmObjectField("Usuario", schema.get("Usuario"))
                .addField("QtdErros", int.class)
                .addField("QtdAcertos", int.class)
                .addField("QtdVezesEstudou", int.class)
                .addField("CardPersonalizado",boolean.class)
                .addField("NaoEstudar",boolean.class);

        //Configuracao
        schema.create("Configuracao")
                    .addField("id", long.class, FieldAttribute.PRIMARY_KEY)
                    .addField("itensPorSessaoRevisao", int.class)
                    .addField("itensPorSessaoEstudo", int.class)
                    .addField("hora", int.class)
                    .addField("minuto", int.class)
                    .addField("usuarioId", long.class);

    }
}
