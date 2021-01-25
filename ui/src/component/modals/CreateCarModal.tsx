import React, { useState } from 'react';
import { Button, Modal, ModalActions, Form, Message, Image, Icon, Card } from 'semantic-ui-react';
import { useForm, Controller } from 'react-hook-form';
import SemanticDatePicker from 'react-semantic-ui-datepickers';
import Select from 'react-select';
import _ from 'lodash';
import { store } from 'react-notifications-component';

import { fetchAllAvailableEngineTypes, fetchAllCarBrands, fetchAllCarModels, saveCarForUser } from '../../api/GarageApi';
import { ICarEngineTypes, ICarBrand, ICarModel } from '../../api/types';
import AddButton from '../AddButton';
import { AxiosResponse } from 'axios';
import 'react-semantic-ui-datepickers/dist/react-semantic-ui-datepickers.css';

interface ICreateCarModalProps {
    refreshList: () => void
}

interface ISelectDto {
    label: string,
    value: number
}

interface IFormData {
    carBrand: ISelectDto,
    carModel: ISelectDto,
    carMileage: number,
    carPhotoUrl: string,
    yearOfProduction: Date,
    engineType: ISelectDto
}

const CreateCarModal: React.FC<ICreateCarModalProps> = ({ refreshList }) => {

    const [engineTypes, setEngineTypes] = useState<ICarEngineTypes[]>([]);
    const [carModels, setCarModels] = useState<ICarModel[]>([]);
    const [carBrands, setCarBrands] = useState<ICarBrand[]>([]);
    const [imageUrl, setImageUrl] = useState<string>("");
    const [open, setOpen] = useState<boolean>(false);
    const { control, errors, setValue, handleSubmit } = useForm<IFormData>();

    const handleModalOpen = async () => {
        let engineTypes: AxiosResponse<ICarEngineTypes[]> = await fetchAllAvailableEngineTypes();
        let carModels: AxiosResponse<ICarModel[]> = await fetchAllCarModels();
        let carBrands: AxiosResponse<ICarBrand[]> = await fetchAllCarBrands();

        if (engineTypes.status === 200) {
            console.log(engineTypes.status);
            setEngineTypes(engineTypes.data);
        }

        if (carModels.status === 200) {
            console.log(carModels.status);
            setCarModels(carModels.data);
        }

        if (carBrands.status === 200) {
            console.log(carBrands.status);
            setCarBrands(carBrands.data);
        }

        setOpen(true);
    }

    const onSubmit = async (data: IFormData) => {
        const user = JSON.parse(localStorage.getItem('user') || 'null');

        await saveCarForUser({
            userId: user.userId,
            carBrandId: data.carBrand.value,
            carModelId: data.carModel.value,
            engineTypeId: data.engineType.value,
            carPhotoUrl: data.carPhotoUrl,
            yearOfProduction: data.yearOfProduction.getTime(),
            carMileage: data.carMileage
        }).then(response => {
            store.addNotification({
                title: 'Pojazdy',
                type: 'success',
                message: response.data.message,
                insert: "bottom",
                container: "bottom-left",
                dismiss: {
                    duration: 4000
                }
            })
            setImageUrl("");
            refreshList();
            setOpen(false);
        }).catch(error => {
            store.addNotification({
                title: 'Rejestracja',
                type: 'warning',
                message: error.response.data.message,
                insert: "bottom",
                container: "bottom-left",
                dismiss: {
                    duration: 4000
                }
            })
        })
    }

    function prepareEngineTypeOptionsDto(): ISelectDto[] {
        const options: ISelectDto[] = [];
        engineTypes.map(engineType => {
            options.push(
                {
                    label: engineType.typeName,
                    value: engineType.typeId
                }
            );
        });

        return _.orderBy(options, ['label'], ['asc']);
    }

    function prepareModelOptionsDto(): ISelectDto[] {
        const options: ISelectDto[] = [];
        carModels.map(carModel => {
            options.push(
                {
                    label: carModel.modelName,
                    value: carModel.modelId
                }
            );
        });

        return _.orderBy(options, ['label'], ['asc']);
    }

    function prepareBrandOptionsDto(): ISelectDto[] {
        const options: ISelectDto[] = [];
        carBrands.map(carBrand => {
            options.push(
                {
                    label: carBrand.brandName,
                    value: carBrand.brandId
                }
            );
        });

        return _.orderBy(options, ['label'], ['asc']);
    }

    return (
        <Modal
            onClose={() => setOpen(false)}
            onOpen={handleModalOpen}
            open={open}
            trigger={
                <Button icon labelPosition="right">
                    Dodaj nowy pojazd
                    <Icon name="add" />
                </Button>
            }
        >
            <Modal.Header>{`Dodaj nowy pojazd`}</Modal.Header>
            <Modal.Content image>
                <Card className="car-img-card">
                    <Image src={imageUrl} wrapped />
                </Card>
                <div className="create-car-form">
                    <Modal.Description>
                        <Form>
                            <Controller
                                control={control}
                                name="carBrand"
                                render={({ value, onChange }) => (
                                    <div className="field">
                                        <label>Model pojazdu:</label>
                                        <Select
                                            label="Model pojazdu:"
                                            placeholder="Wybierz model pojazdu:"
                                            value={value}
                                            options={prepareBrandOptionsDto()}
                                            onChange={(e) => { onChange(e) }}
                                        />
                                    </div>
                                )}
                            />

                            <Controller
                                control={control}
                                name="carModel"
                                render={({ value, onChange }) => (
                                    <div className="field">
                                        <label>Model pojazdu:</label>
                                        <Select
                                            label="Marka pojazdu:"
                                            placeholder="Wybierz markę pojazdu:"
                                            value={value}
                                            options={prepareModelOptionsDto()}
                                            onChange={(e) => { onChange(e) }}
                                        />
                                    </div>
                                )}
                            />

                            <Controller
                                control={control}
                                name="engineType"
                                render={({ value, onChange }) => (
                                    <div className="field">
                                        <label>Paliwo:</label>
                                        <Select
                                            label="Paliwo"
                                            placeholder="Wybierz typ silnika:"
                                            value={value}
                                            options={prepareEngineTypeOptionsDto()}
                                            onChange={(e) => { onChange(e) }}
                                        />
                                    </div>
                                )}
                            />

                            <Controller
                                control={control}
                                name="carMileage"
                                render={({ onChange }) => (
                                    <Form.Input
                                        label="Przebieg w km"
                                        placeholder="Obecny przebieg"
                                        type="number"
                                        onChange={(e) => onChange(e)}
                                    />
                                )}
                                defaultValue=""
                            />

                            <Controller
                                control={control}
                                name="yearOfProduction"
                                render={({ onChange }) => (
                                    <SemanticDatePicker
                                        placeholder="Rok produkcji"
                                        format="YYYY"
                                        label="Rok produkcji"
                                        locale="pl-PL"
                                        type="basic"
                                        onChange={(event, data) => {
                                            onChange(data.value);
                                        }}
                                    />
                                )}
                            />

                            <Controller
                                control={control}
                                name="carPhotoUrl"
                                render={({ onChange }) => (
                                    <Form.Input
                                        label="Url do zdjęcia"
                                        placeholder="Url do zdjęcia"
                                        onChange={(e) => {
                                            onChange(e);
                                            setImageUrl(e.target.value);
                                        }}
                                    />
                                )}
                                defaultValue=""
                            />
                        </Form>
                    </Modal.Description>
                </div>
            </Modal.Content>
            <ModalActions>
                <AddButton text="Dodaj pojazd" handleClick={handleSubmit(onSubmit)} />
                <Button onClick={() => setOpen(false)}>
                    Zamknij
                </Button>
            </ModalActions>
        </Modal>
    )
}

export default CreateCarModal;