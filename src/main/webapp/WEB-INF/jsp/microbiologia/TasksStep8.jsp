<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
            <%@page contentType="text/html" pageEncoding="UTF-8" %>
                <div class="step8 pb-5 row col-11 mx-auto" data-wizard-type="step-content">
                    <div class="row">
                        <div class="col-12 row d-flex justify-content-between border-bottom pb-3"
                            style="margin-bottom: 20px">
                            <h2 id="txtTaskStep8TestName" style="text-align: center;width: 100%" class="col-12 mt-20">
                                Cargando...</h2>
                        </div>

                    <div class="col-8">
     
                            <div class="col-3" style="text-align: left; float: left; color:white;">.

                             </div>

                        <div class="col-12">        
                            <div class="form-group row">
                                <label class="col-3 mt-5" style="text-align:right;">N° Aislado:</label>
                                
                                <div class="col-3 mt-2">
                                    <select id="selTaskStep8MONumber" class="form-control" onchange="changeMOSelector(this);">
                                    </select>
                                </div>    
                                
                                
                                <label class="col-1 mt-5">de</label>

                                <div class="col-3 mt-2">
                                    <input disabled="true" value="0" id="txtTaskStep8MONumber" class="form-control">
                                </div>   


                            </div>  
                        </div> 
                        
                        <div class="form-group row">        
                            <div class="col-2 mt-2">
                            </div>    

                            <label class="col-3 mt-5 ml-10" style="text-align:right;">Microorganismo: </label><div class="col-5 mt-2">
                                <input disabled="true" value="No elegido" id="txtTaskStep8MOName" class="form-control">
                            </div>   

                            <div class="col-1"> 
                            </div>   
                        </div>

                        <div class="col-12">
                            
                                <div class="col-3" style="text-align: left; float: left;">
                                    <a id="btnStep8AddAB" class="navi-link" data-toggle="modal" data-target="#step8OptAB">
                                        <span class="symbol symbol-50 symbol-circle mr-3 ">
                                            <span id="spnStep5ScanTest" class="symbol-label bg-hover-primary  hoverIcon" href="#" data-toggle="tooltip" title="Antibióticos opcionales">
                                                <i id="iClearAGFilterddd" class="la la-list icon-xl text-primary">
                                                </i>
                                            </span>
                                        </span>
                                        <span class="navi-text"></span>
                                    </a>
                                    <a id="btnStep5ScanTest" class="navi-link" data-toggle="modal" data-target="#step8AddAB">
                                        <span class="symbol symbol-50 symbol-circle mr-3 ">
                                            <span id="spnStep5ScanTest" class="symbol-label bg-hover-primary  hoverIcon" href="javascript:void(0)" data-toggle="tooltip" title="Agregar antibióticos">
                                                <i id="iClearAGFilterddd" class="la la-plus icon-xl text-primary">
                                                </i>
                                            </span>
                                        </span>
                                        <span class="navi-text"></span>
                                    </a>
                                </div>

                                <div class="form-group row">    
                                    <label class="col-3 mt-3" style="text-align:right;">Antibiograma:</label>
                                
                                    <div class="col-7">
                                        <select id="step8AntibiogramSelect" class="form-control" onchange="loadAGData(this);">
                                        </select>
                                    </div>
                                </div>
                            
                        </div>

                    
                    
                        <div class="row">
                            <div class="col-12">
                            <table id="tableStep8ABDetails" class="table table-hover col-12">
                                <thead>
                                    <tr>
                                        <th scope="col">ID</th>
                                        <th scope="col">Antibiótico</th>
                                        <th scope="col">CIM</th>
                                        <th scope="col">Diámetro</th>
                                        <th scope="col">Interpretación</th>
                                        <th scope="col">Incluir en Informe</th>
                                        <th scope="col">Eliminar</th>
                                    </tr>
                                </thead>
                                <tbody id="tableDataStep8ABDetails">
                                </tbody>
                            </table>
                        </div>
                        </div>

                    </div>
                        

                        <div class="col-4">
                            <div class="row">   
                                <div class="col-12 mb-10">
                                <table id="tableStep8MarkersDetails" class="table table-hover">
                                    <thead>
                                        <tr>
                                            <th scope="col">Marcador de Resistencia</th>
                                            <th scope="col">Resultado</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tableDataStep8MarkersDetails">
                                        
                                    </tbody>
                                </table>
                            </div>        

                            <div class="form-group row">
                                <div class="col-1"> 

                                </div>
    
                                <div class="col-3">
                                    <div class="float" style="text-align: center; float: none;">
                                        <a id="btnStep5ScanTest" class="navi-link">
                                            <span class="symbol symbol-50 symbol-circle mr-3 ">
                                                <span id="spnStep5ScanTest" class="symbol-label bg-hover-primary  hoverIcon"
                                                    href="javascript:void(0)" data-toggle="tooltip" title="Lectura de código de barras">
                                                    <i id="iClearAGFilteddddd" class="la la-print icon-xl text-primary">
                                                    </i>
                                                </span>
                                            </span>
                                            <span class="navi-text"></span>
                                        </a>
                                    </div>
                                </div>
    
                                <div class="col-7">
                                    <div class="form-group row">
                                        <div class="mt-1 form-check justify-content-end">
                                            <input class="form-check-input-reverse" type="checkbox" value="" id="chkStep5Preview"
                                            style="margin-left: 10px;">
                                            <label class="form-check-label pull-right" for="chkStep5Preview" style="float:left;">
                                                Vista previa
                                            </label>
                                            <br>
                                            <input class="form-check-input-reverse" type="checkbox" value="" id="chkStep5PreReport"
                                                style="margin-left: 15px;">
                                            <label class="form-check-label pull-right" for="chkStep5PreReport" style="float:left;">
                                                Preinforme
                                            </label>
                                        </div>
                                    </div>
                                </div>   
                            </div> 
                        </div>
                     </div>
                            
                            
                    </div>
                        
                </div>
            