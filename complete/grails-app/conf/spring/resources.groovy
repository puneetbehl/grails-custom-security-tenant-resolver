//tag::tenantResolverImport[]
import demo.CurrentUserTenantResolver
//end::tenantResolverImport[]
//tag::passwordEncodingImport[]
import demo.UserPasswordEncoderListener
//end::passwordEncodingImport[]
// Place your Spring DSL code here
//tag::beans[]
beans = {
//end::beans[]
//tag::passwordEncodingBean[]
    userPasswordEncoderListener(UserPasswordEncoderListener)
//end::passwordEncodingBean[]
//tag::tenantResolverBean[]
    CurrentUserTenantResolver(CurrentUserTenantResolver)
//end::tenantResolverBean[]
}
