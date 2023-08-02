const orderHtmlTemplate = `<div class="form-group row align-items-center">
                                <div class="col">
                                    <label>{id}</label>
                                </div>
                                <div class="col">
                                    <label>{date}</label>
                                </div>
                                <div class="col-2">
                                    <label>{name}</label>
                                </div>
                                <div class="col">
                                    <label>{gender}</label>
                                </div>
                                <div class="col">
                                    <label>{age}</label>
                                </div>
                                <div class="col">
                                    <label>{attentionType}</label>
                                </div>
                                <div class="col">
                                    <label>{institution}</label>
                                </div>
                                <div class="col">
                                    <label>{service}</label>
                                </div>
                                <div class="col">
                                    <button type="button" class="btn" onclick="orderDetail('{id}')">Ver</button>
                                    <a href="#" onclick="getOrderDetail('{id}');" class="p-2" title="Datos Paciente"><i class="fas fa-user-md" aria-hidden="true"></i></a>
                                    <a href="#" onclick="getOrderEventsDetail('{id}');" class="p-2" title="Orden/Exámenes">  <i class="fa fa-search" aria-hidden="true"></i></a>
                                </div>
                            </div>`;

const examHtmlTemplate = `<div class="form-group row align-items-center">
                            <div class="col">
                                <label>{exam}</label>
                            </div>
                            <div class="col">
                                <label>{sample}</label>
                            </div>
                            <div class="col-2">
                                <label>{bodyPart}</label>
                            </div>
                            <div class="col">
                                <label>{processStatus}</label>
                            </div>
                            <div class="col-2">
                                <label data-toggle="tooltip" title="">{seedingDate}</label>
                            </div>
                            <div class="col-2">
                                <label data-toggle="tooltip" title="">{reseedingDate}</label>
                            </div>
                            <div class="col-1">
                                <label>{urgency}</label>
                            </div>
                            <div class="col">
                                <button type="button" class="btn" onclick="examDetail('{id}','{orderId}')">Ver</button>
                                <a href="#" onclick="getOpcTestExamenDetail('{id}','{orderId}');" class="p-2" title="Tests Opcionales"><i class="fas fa-vials" aria-hidden="true"></i></a>
                                <a href="#" onclick="getObsExamenDetail('{orderId}','{id}');" class="p-2" title="Observaciones"><i class="fa fa-search" aria-hidden="true"></i></a>  
                                <a href="#" onclick="getNotasExamenDetail('{orderId}','{id}');" class="p-2" title="Notas"><i class="fas fa-clipboard" aria-hidden="true"></i></a>
                            </div>
                        </div>`;

const testHtmlTemplate = `<div id=testsListDataRow-{id} class="form-group row align-items-center" style="">
                            <div class="col-3">
                                <label>{test}</label>
                            </div>
                            <div id="testResultDiv-{id}" class="col-3">
                            </div>
                            <div class="col-2">
                                <label>{status}</label>
                            </div>
                            <div id="testMicrobiologyStatusDiv-{id}" class="col-2">
                            </div>
                            <div class="col-1">
                                <button type="button" class="btn" onclick="testDetail('{orderId}', '{examId}', '{id}')">Ver</button>
                            </div>
                        </div>`;

const eventsRowHtmlTemplate = `<tr class='pointer'>
                                    <td class='mx-auto'><span>{date}</span></td>
                                    <td class='mx-auto'><span>{user}</span></td>
                                    <td class='mx-auto'><span>{field}</span></td>
                                    <td class='mx-auto'><span>{oldValue}</span></td>
                                    <td class='mx-auto'><span>{newValue}</span></td>
                                </tr>`;

const optionalTestHtmlTemplate = `<div class="col">
                                    <div class="form-group row">
                                        <label id="chkExitus" class="checkbox checkbox-primary ml-2 ocultar">
                                            <input id="optionalTest-{id}" name="exitus" type="checkbox" class="" onchange="optionalTestDisplay( '{id}' )">{name}
                                            <span></span>
                                        </label>
                                    </div>
                                  </div>`

const antibiogramHtmlTemplate = `<div class="form-group row align-items-center">
                                    <div class="col-2">
                                        <label>{antibiotic}</label>
                                    </div>
                                    <div class="col-1">
                                        <input class="form-control" id="antibioticCim-{abName}" type="text"/>
                                    </div>
                                    <div class="col-1">
                                        <input class="form-control" id="antibioticDiameter-{abName}" type="text"/>
                                    </div>
                                    <div class="col-2">
                                        <select id="antibioticInterpretation-{abName}" class="form-control">
                                        </select>
                                    </div>
                                    <div class="col-1">
                                        <label class="checkbox checkbox-primary ml-4 ocultar">
                                            <input id="antibioticIncludeInReport-{abName}" type="checkbox" class="">
                                            <span></span>
                                        </label>
                                    </div>
                                    <div class="col-1">
                                        <button type="button" class="btn" onclick="deleteAntibiotic('{antibiotic}')">Borrar</button>
                                    </div>
                                </div>`

const resistanceMethodHtmlTemplate = `<div class="form-group row align-items-center">
                                <div class="col-4">
                                    <label>{method}</label>
                                </div>
                                <div class="col-4">
                                    <select id="methodResult-{id}" class="form-control">
                                    </select>
                                </div>
                                <div class="col-1">
                                    <button type="button" class="btn" onclick="deleteMethod('{id}')">Borrar</button>
                                </div>
                            </div>`

const microorganismHtmlTemplate = `<tr>
                                        <td class='mx-auto'><label>{id}</label></td>
                                        <td class='mx-auto'><label>{code}</label></td>
                                        <td class='mx-auto'><label>{name}</label></td>
                                        <td class='mx-auto'><button type="button" class="btn" onclick=showMicroorganismDetail('{code}')>Ver</button></td>
                                    </tr>`;

const antibioticHtmlTemplate = `<tr>
                                    <td class='mx-auto'><label>{id}</label></td>
                                    <td class='mx-auto'><label>{code}</label></td>
                                    <td class='mx-auto'><label>{name}</label></td>
                                    <td class='mx-auto'><button type="button" class="btn" onclick=showAntibioticDetail('{id}')>Ver</button></td>
                                </tr>`;



const resistanceHtmlTemplate = `<tr class="resistanceRow">
                                    <td>
                                        <div class="col-12">
                                            <input type="hidden" id="txtIdABDetails{id}" value="{id}" class="id" disabled="true">
                                            <input type="hidden" id="txtMethodABDetails{id}" value="{method}" class="method" disabled="true">
                                            <select id="txMOABDetails{id}" class="form-control">
                                                <option value="">-</option>
                                            </select>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="row">
                                            <div class="col-8">
                                                <input id="txtSensibleABDetails{id}"
                                                    type="text" class="form-control" autocomplete="off"
                                                    placeholder="" value="{sensible}">
                                            </div>
                                            <div class="col-4">
                                                <select id="txSensibleSignABDetails{id}" class="form-control">
                                                    <option value="<=">&le;</option>
                                                    <option value="<">&lt;</option>
                                                    <option value=">=">&ge;</option>
                                                    <option value=">">&gt;</option>
                                                </select>
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="row-3">
                                            <label class="col-3 col-form-label">-</label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="row">
                                            <div class="col-4">
                                                <select id="txResistantSignABDetails{id}" class="form-control">
                                                    <option value="<=">&le;</option>
                                                    <option value="<">&lt;</option>
                                                    <option value=">=">&ge;</option>
                                                    <option value=">">&gt;</option>
                                                </select>
                                            </div>
                                            <div class="col-8">
                                                <input id="txtResistantABDetails{id}"
                                                    type="text" class="form-control" autocomplete="off"
                                                    placeholder="" value="{resistant}">
                                            </div>
                                        </div>
                                    </td>
                                </tr>`
