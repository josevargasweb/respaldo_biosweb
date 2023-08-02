/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function Observer(id,handler){
    
    this.id=id;
    this.consumer=handler;
}

function Queue(evento){
    
    this.mievento = evento;
    this.misElementos = new Array();
    
}

Queue.prototype.listen= function (id,handler){

    let observer = new Observer(id,handler)
    if (observer.consumer === undefined || observer.consumer === null){
        throw "No tiene definido consumer";
    } 
    this.misElementos.push(observer); 
    
};



Queue.prototype.broadcast = function (event){
    this.misElementos.forEach(observer => observer.consumer(observer.id));
};


