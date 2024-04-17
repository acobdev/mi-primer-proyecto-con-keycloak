<#import "template.ftl" as layout>
<@layout.registrationLayout displayMessage=false; section>
    <#if section="header">
        <h2>Encuesta de Opinión:</h2>
    <#elseif section="form">
        <div id="kc-terms-text">
            <p>¿Cómo te sientes hoy?</p>
        </div>
        <form class="form-actions" action="${url.loginAction} method="POST">
            <div style="display: flex; flex-direction: column">
                <div>
                    <input type="radio" id="estupendo" name="opinion" value="estupendo" checked>
                    <label for="estupendo">¡Estupendamente!</label>
                </div>
                <div>
                     <input type="radio" id="bien" name="opinion" value="bien">
                     <label for="bien">Es un día superior a la media</label>
                </div>
                <div>
                     <input type="radio" id="normal" name="opinion" value="normal">
                     <label for="normal">Es un día normal</label>
                </div>
                <div>
                     <input type="radio" id="cansado" name="opinion" value="cansado">
                     <label for="cansado">Hoy estoy cansado...</label>
                </div>
                <div>
                      <input type="radio" id="estresado" name="opinion" value="estresado">
                      <label for="estresado">El estrés me está matando...</label>
                </div>
            </div>
            <input class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonLargeClass!}" name="aceptar" id="kc-accept" type="submit" value="Aceptar">
            <input class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonLargeClass!}" name="saltar" id="kc-decline" type="submit" value="No deseo responder">
        </form>
        <div class="clearfix"></div>
    </#if>
</@layout.registrationLayout>