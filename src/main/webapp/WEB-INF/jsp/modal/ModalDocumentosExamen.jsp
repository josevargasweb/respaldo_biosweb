<div
  class="modal fade"
  id="testDocModal"
  tabindex="-1"
  role="dialog"
  aria-hidden="true"
>
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-body">
        <div class="col-12">
          <h5>Adjuntar documento</h5>
          <form id="formOM" class="file-container" method="post" enctype="multipart/form-data">
            <input
              id="ordenMedicaFile"
              name="ordenMedicaFile"
              multiple
              type="file"
              class="file doc-orden"
            />
          </form>
        </div>
        <div
          class="col-12 pl-0 pr-0 d-flex mt-3 justify-content-between flex-row-reverse"
        >
          <button
            type="button"
            class="btn btn-blue-primary btn-lg font-weight-bold mt-2 mr-2"
            data-dismiss="modal"
          >
            Cerrar
          </button>
        </div>
      </div>
    </div>
  </div>
</div>
